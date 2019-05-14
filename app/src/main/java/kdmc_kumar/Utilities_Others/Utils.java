package kdmc_kumar.Utilities_Others;

import java.io.InputStream;
import java.io.OutputStream;

class Utils {
    Utils() {
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            while (true) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ignored) {
        }
    }
}