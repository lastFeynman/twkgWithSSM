package twkg.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	public static final String COOKIE_NAME;
	public static final int COOKIE_EXPIRED;
	
	public static final String SONG_PATH;
	public static final String COVER_SONG_PATH;
	public static final String THUMBNAIL_PATH;
	public static final String LYRIC_PATH;
	public static final String USER_AVATAR_PATH;
	
	static {
		Properties configs = new Properties();
		InputStream configIn = ConfigUtil.class.getResourceAsStream("../../twkg-conf.properties");
		if(configIn == null) {
			System.err.println("配置文件读取失败");
			System.exit(-1);
		}
		try {
			configs.load(configIn);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(configIn != null) {
				try {
					configIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		COOKIE_NAME = configs.getProperty("cookieName");
		COOKIE_EXPIRED = Integer.parseInt(configs.getProperty("cookieExpired"));

		SONG_PATH = configs.getProperty("songPath");
		COVER_SONG_PATH = configs.getProperty("coverSongPath");
		THUMBNAIL_PATH = configs.getProperty("thumbnailPath");
		LYRIC_PATH = configs.getProperty("lyricPath");
		USER_AVATAR_PATH = configs.getProperty("userAvatarPath");
	}
}
