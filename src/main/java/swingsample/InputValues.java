package swingsample;

import lombok.Data;

@Data
public class InputValues {
    private String archiveFilesPath;
    private String docType;
    private String archiveId;
    private String bookingCenter;
    private String archivalSys;
    private String date;
    private int versionNum;
    private int seqNumStart;
    private int seqNumEnd;
    private int maxFolderSizeGB;
    private int maxDataFiles;
    private String docId;
    private String typeOfMailing;
    private String title;
    private String jobId;
}
