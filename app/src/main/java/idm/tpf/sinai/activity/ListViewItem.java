package idm.tpf.sinai.activity;

public class ListViewItem {
	
    public final String thumbnailPath;       // the drawable for the ListView item ImageView
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description
    public final String filePath;
    public final String date;
    
    public ListViewItem(String thumbnailPath, String title, String description,String filePath,String date) {
        this.thumbnailPath = thumbnailPath;
        this.title = title;
        this.filePath=filePath;
        this.description = description;
        this.date=date;
    }
}