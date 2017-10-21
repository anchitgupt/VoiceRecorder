package voicerecorder.ateam.com.voicerecorder.Database;

import android.provider.BaseColumns;

/**
 * Created by apple on 21/10/17.
 */

final public class VoiceContract {
    public VoiceContract() {
    }

    public static final class VoiceEntry implements BaseColumns {

        public static final String TABLE_NAME = "AUDIO";

        public static final String ID = BaseColumns._ID;

        public static final String NAME = "name";

        public static final String LENGTH = "length";

        public static final String LOCATION = "loc";

        public static final String DATES = "time";
    }
}
