package cn.imeth.android.image.choose;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/10.
 */
public class PhotoFolder {

    /**
     * 相册文件夹
     */
    String dir;
    /**
     * 第一张图片路径
     */
    String firstPhotoPath;
    /**
     * 文件夹的名称
     */
    String name;
    /**
     * 数量
     */
    int count;
    /**
     * 最后修改时间
     */
    long lastModifiedTime;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getFirstPhotoPath() {
        return firstPhotoPath;
    }

    public void setFirstPhotoPath(String firstPhotoPath) {
        this.firstPhotoPath = firstPhotoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
