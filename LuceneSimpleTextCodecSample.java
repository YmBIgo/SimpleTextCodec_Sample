import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneSimpleTextCodecSample {

	private static File plaintextDir;
	private static String INDEX_ROOT_FOLDER = "/Users/coffeecup/Documents/programming/Java/lucene/lucene/lucene/core/src/java";

    private static File assureDirectoryExists(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

	public static void main(String[] args) {
		plaintextDir = assureDirectoryExists(new File(INDEX_ROOT_FOLDER, "lucene-plaintext"));
		// 
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		config.setCodec(new SimpleTextCodec());
		try {
			Directory luceneDir = FSDirectory.open(plaintextDir.toPath());
			IndexWriter writer = new IndexWriter(luceneDir, config);
			writer.addDocument(Arrays.asList(
					new TextField("title", "The title of my first document", Store.YES),
					new TextField("content", "The content of the first document", Store.YES)
			));
			writer.addDocument(Arrays.asList(
					new TextField("title", "The title of my second document", Store.YES),
					new TextField("content", "The content of the second document", Store.YES)
			));
			writer.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}