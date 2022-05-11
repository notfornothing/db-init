package oldmoon.api.yaml;

import java.io.File;

public class LoadFile {

    public static File load(String path, String fileName){
        return new File(path + fileName);
    }
}
