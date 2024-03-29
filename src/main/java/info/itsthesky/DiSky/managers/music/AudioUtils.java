package info.itsthesky.DiSky.managers.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import info.itsthesky.DiSky.skript.audio.ExprLastPlayedAudio;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioUtils {

    public static HashMap<AudioPlayer, AudioData> MUSIC_DATA = new HashMap<>();
    public static AudioPlayerManager MANAGER;
    public static YoutubeAudioSourceManager YOUTUBE_MANAGER_SOURCE;
    public static final YoutubeSearchProvider YOUTUBE_MANAGER_SEARCH = new YoutubeSearchProvider();
    public static Map<Long, GuildAudioManager> MUSIC_MANAGERS;
    private static final Map<Long, EffectData> GUILDS_EFFECTS = new HashMap<>();

    public static EffectData getEffectData(Guild guild) {
        if (!GUILDS_EFFECTS.containsKey(guild.getIdLong())) {
            EffectData data = new EffectData(guild);
            GUILDS_EFFECTS.put(guild.getIdLong(), data);
            return data;
        } else {
            return GUILDS_EFFECTS.get(guild.getIdLong());
        }
    }

    public static void initializeAudio() {
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        playerManager.getConfiguration().setFilterHotSwapEnabled(true);
        MANAGER = playerManager;
        MUSIC_MANAGERS = new HashMap<>();
        AudioSourceManagers
                .registerRemoteSources(playerManager);
        AudioSourceManagers
                .registerLocalSource(playerManager);
        YOUTUBE_MANAGER_SOURCE = new YoutubeAudioSourceManager();
    }

    public static AudioTrack[] search(String... queries) {
        List<AudioTrack> results = new ArrayList<>();
        AudioItem playlist = null;
        for (String query : queries) {
            playlist = YOUTUBE_MANAGER_SEARCH.loadSearchResult(query, data -> new YoutubeAudioTrack(data, YOUTUBE_MANAGER_SOURCE));
            if (playlist instanceof AudioPlaylist) {
                AudioPlaylist playlist1 = (AudioPlaylist) playlist;
                results.addAll(playlist1.getTracks());
            }
        }
        return results.isEmpty() ? null : results.toArray(new AudioTrack[0]);
    }

    public static String[] searchURLs(String... queries) {
        List<String> results = new ArrayList<>();
        AudioItem playlist = null;
        for (String query : queries) {
            playlist = YOUTUBE_MANAGER_SEARCH.loadSearchResult(query, data -> new YoutubeAudioTrack(data, YOUTUBE_MANAGER_SOURCE));
            if (playlist instanceof AudioPlaylist) {
                AudioPlaylist playlist1 = (AudioPlaylist) playlist;
                playlist1.getTracks().forEach((r) -> {
                    results.add("https://www.youtube.com/watch?v=" + r.getIdentifier());
                });
            }
        }
        return results.isEmpty() ? new String[0] : results.toArray(new String[0]);
    }

    public static synchronized GuildAudioManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildAudioManager musicManager = MUSIC_MANAGERS.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildAudioManager(guild, guild.getJDA());
            MUSIC_MANAGERS.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

    public static void play(Guild guild, GuildAudioManager musicManager, AudioTrack track, Member member) {
        connectToFirstVoiceChannel(guild.getAudioManager(), member);
        musicManager.trackScheduler.queue(track);
        ExprLastPlayedAudio.lastTrack = track;
    }

    private static void connectToFirstVoiceChannel(AudioManager audioManager, Member member) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            audioManager.openAudioConnection(member.getVoiceState().getChannel());
        }
    }

    public static void skipTrack(Guild guild) {
        GuildAudioManager musicManager = getGuildAudioPlayer(guild);
        musicManager.trackScheduler.nextTrack();
    }

    public static void loadAndPlay(final Guild guild, final AudioTrack track, Member member) {
        GuildAudioManager musicManager = getGuildAudioPlayer(guild);
        MANAGER.loadItemOrdered(musicManager, track.getIdentifier(), new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                play(guild, musicManager, track, member);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                if (firstTrack == null) firstTrack = playlist.getTracks().get(0);
                play(guild, musicManager, firstTrack, member);
            }

            @Override
            public void noMatches() { }

            @Override
            public void loadFailed(FriendlyException exception) { }
        });
    }
}
