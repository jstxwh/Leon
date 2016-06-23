package lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.queries.function.FunctionQuery;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TimeLimitingCollector;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.spans.SpanFirstQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Counter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LuceneHighOperations {
	private IndexWriter writer;
	private IndexReader reader;

	/**
	 * <p><b>Description:</b> IndexWriter的参数构成为<br/>
	 * 文件系统目录: 要为其传递一个文件路径，目的是让IndexWriter对象能够找到索引文件<br/>
	 * IndexWriterConfig: 主要是配置分词器信息，告诉IndexWriter要用什么样的分词器解析索引文件</p>
	 * @throws IOException
	 */
	@Before
	public void before() throws IOException {
		writer = new IndexWriter(FSDirectory.open(Paths
				.get("D:/Download/index")), new IndexWriterConfig(
				new StandardAnalyzer()));
		reader=DirectoryReader.open(writer);
	}
	/**
	 * <p><b>Description:</b> SpanFirstQuery的用法</p>
	 * @throws IOException
	 */
	@Test
	public void spanFirstQueryTest() throws IOException{
		IndexSearcher searcher=new IndexSearcher(reader);
		SpanFirstQuery query = new SpanFirstQuery(new SpanTermQuery(new Term("Value","book")),300);
		TopDocs docs = searcher.search(query, 5);
		ScoreDoc[] scoreDocs=docs.scoreDocs;
		for(ScoreDoc s:scoreDocs){
			Document doc = searcher.doc(s.doc);
			Iterator<IndexableField> iterator = doc.iterator();
			while(iterator.hasNext()){
				IndexableField next = iterator.next();
				System.out.print(next.name()+"-----"+next.stringValue()+"\t");
			}
			System.out.println();
		}
	}
	@Test
	public void timeLimitingCollectorTest() throws IOException{
		IndexSearcher searcher=new IndexSearcher(reader);
		SpanFirstQuery query = new SpanFirstQuery(new SpanTermQuery(new Term("Value","book")),300);
		TopScoreDocCollector topScoreDocCollector = TopScoreDocCollector.create(10);
		TimeLimitingCollector collector = new TimeLimitingCollector(topScoreDocCollector,Counter.newCounter(false),1000);
		searcher.search(query, collector);
		TopDocs docs = topScoreDocCollector.topDocs();
		ScoreDoc[] scoreDocs=docs.scoreDocs;
		for(ScoreDoc s:scoreDocs){
			Document doc = searcher.doc(s.doc);
			Iterator<IndexableField> iterator = doc.iterator();
			while(iterator.hasNext()){
				IndexableField next = iterator.next();
				System.out.print(next.name()+"-----"+next.stringValue()+"\t");
			}
			System.out.println();
		}
	}
	/**
	 * <p><b>Description:</b> 多索引搜索: 通过MultiReader实现</p>
	 * @throws IOException
	 */
	@Test
	public void multiSearchTest() throws IOException{
		MultiReader multiReader=new MultiReader(reader);
		IndexSearcher searcher=new IndexSearcher(multiReader);
		SpanFirstQuery query = new SpanFirstQuery(new SpanTermQuery(new Term("Value","book")),300);
		TopDocs docs = searcher.search(query, 5);
		ScoreDoc[] scoreDocs=docs.scoreDocs;
		for(ScoreDoc s:scoreDocs){
			Document doc = searcher.doc(s.doc);
			Iterator<IndexableField> iterator = doc.iterator();
			while(iterator.hasNext()){
				IndexableField next = iterator.next();
				System.out.print(next.name()+"-----"+next.stringValue()+"\t");
			}
			System.out.println();
		}
	}
	/**
	 * <p><b>Description:</b> TermVector: 待完善！！！！</p>
	 * @throws IOException
	 */
	@Test
	public void termVectorTest() throws IOException{
		IndexSearcher searcher=new IndexSearcher(reader);
		SpanFirstQuery query = new SpanFirstQuery(new SpanTermQuery(new Term("Value","book")),300);
		TopDocs docs = searcher.search(query, 5);
		ScoreDoc[] scoreDocs=docs.scoreDocs;
		for(ScoreDoc s:scoreDocs){
			Document doc = searcher.doc(s.doc);
			Fields fields = reader.getTermVectors(s.doc);
			System.out.println(fields);
			Iterator<IndexableField> iterator = doc.iterator();
			while(iterator.hasNext()){
				IndexableField next = iterator.next();
				System.out.print(next.name()+"-----"+next.stringValue()+"\t");
			}
			System.out.println();
		}
	}
	/**
	 * <p><b>Description:</b> 功能查询实现自定义评分</p>
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Test
	public void customScoreQueryTest() throws IOException, ParseException{
		IndexSearcher searcher=new IndexSearcher(reader);
		QueryParser parser = new QueryParser("Value", new StandardAnalyzer());
		Query query = parser.parse("Name:Lucene AND \"Hello World\"~5");
		FunctionQuery function=new FunctionQuery(new ValueSource(){
			@Override
			public FunctionValues getValues(Map context,
					LeafReaderContext readerContext) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean equals(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int hashCode() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String description() {
				// TODO Auto-generated method stub
				return null;
			}});
		CustomScoreQuery customQuery=new CustomScoreQuery(query,function);
		TopDocs docs = searcher.search(customQuery, 5);
		ScoreDoc[] scoreDocs=docs.scoreDocs;
		for(ScoreDoc s:scoreDocs){
			Document doc = searcher.doc(s.doc);
			Iterator<IndexableField> iterator = doc.iterator();
			while(iterator.hasNext()){
				IndexableField next = iterator.next();
				System.out.print(next.name()+"-----"+next.stringValue()+"\t");
			}
			System.out.println();
		}
	}
	/**
	 * <p><b>Description:</b> 操作完之后要对IndexWriter执行关流操作</p>
	 * @throws IOException
	 */
	@After
	public void after() throws IOException {
		reader.close();
		writer.close();
	}
}
