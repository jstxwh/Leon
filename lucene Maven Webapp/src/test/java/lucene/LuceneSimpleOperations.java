package lucene;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.FloatDocValuesField;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.CustomAnalyzer;
/**
 * <p><b>Description:</b> Lucene API
 * <br/>本文档是基于Lucene 6.0.0版jar包实现的</p>
 * <p><b>Date:</b> 2016年6月8日下午3:06:20</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class LuceneSimpleOperations {
	private IndexWriter writer;
	private Analyzer analyzer;
	private IndexWriterConfig config;
	/**
	 * <p><b>Description:</b> IndexWriter的参数构成为<br/>
	 * 文件系统目录: 要为其传递一个文件路径，目的是让IndexWriter对象能够找到索引文件<br/>
	 * IndexWriterConfig: 主要是配置分词器信息，告诉IndexWriter要用什么样的分词器解析索引文件</p>
	 * @throws IOException
	 */
	@Before
	public void before() throws IOException {
		Map<String,Analyzer> map=new HashMap<String,Analyzer>();
		map.put("Value", new CustomAnalyzer());
		//第一个参数为默认Analyzer
		analyzer = new PerFieldAnalyzerWrapper(new KeywordAnalyzer(),map);
		config = new IndexWriterConfig(analyzer);
		writer = new IndexWriter(FSDirectory.open(Paths.get("C:/index")), config);
	}
	@Test
	public void analyzerTest(){
	}
	/**
	 * <p><b>Description:</b> 创建索引: <br/>
	 * Analyze与是否建索引无必然联系，如果无Analyze，只是表示不会将域值分割成Token而已，仍要建索引<br/>
	 * Field:<br/>
	 * 1、如果将setTokenized(Boolean)设置为false，则尤其适用于精确匹配的搜索<br/>
	 * 2、setOmitNorms(Boolean)如为true，则不保存norms信息，setTokenized(Boolean)为false情况下适用，为true情况下会消耗大量内存<br/>
	 * 3、setStored(Boolean)默认为false，表示不存储索引内容，通常与setTokenized(true)连用，索引比较大文本域值<br/>
	 * 4、IndexOptions.NONE表示不被索引<br/>
	 * 如果确定该域(Field)只用于纯布尔搜索，并且也不会为相关评分做贡献的话，则可直接使用IndexOptions.DOCS<br/>
	 * IndexOptions.DOCS_AND_FREQS_AND_POSITIONS对倒排索引使用Vector Space Model有意义，PhraseQuery和SpanQuery会用到<br/>
	 * 5、可以通过工具类CompressionTools对存储的值进行压缩，但相应的解压缩要消耗CPU，建议少用或不用压缩</p>
	 * @throws IOException
	 */
	@Test
	public void createIndex() throws IOException {
		List<Document> docs = new ArrayList<Document>();
		FieldType type = new FieldType();
		type.setStored(true);//可以选择是否存储，默认为false
		type.setIndexOptions(IndexOptions.DOCS);//默认为null
		type.setOmitNorms(true);//表示是否存储norms信息
		type.setTokenized(true);//如果设置为false，表示不被Analyze，默认为true，如果设置为false，则查询时由于找不到索引而无法查询
		Document doc = new Document();
		doc.add(new IntPoint("Age",15));
		doc.add(new FloatDocValuesField("Score", 80.5f));
		doc.add(new Field("Name","Tom Hanks",type));
		docs.add(doc);
		doc = new Document();
		doc.add(new IntPoint("Age",25));
		doc.add(new FloatDocValuesField("Score", 76.5f));
		doc.add(new Field("Name","Tom Cruise",type));
		docs.add(doc);
		doc = new Document();
		doc.add(new IntPoint("Age",8));
		doc.add(new FloatDocValuesField("Score", 78.8f));
		doc.add(new Field("Name","Johnny Depp",type));
		docs.add(doc);
		writer.addDocuments(docs);
	}
	/**
	 * <p><b>Description:</b> Field排序: <br/>
	 * 如果域为数值类型，在将其加入doc和排序时，要使用NumericDocValuesField类来表示。且不会被存储<br/>
	 * 如果域为文本类型，则使用Field类，且setTokenized(false)，从而避免对其Analyze<br/>
	 * 如果域无加权操作，则不要使用norms选项，从而可节省磁盘和内存空间<br/>
	 * 域的加权操作: setBoost(float boost)</p>
	 * <p><b>Since:</b> v1.0</p>
	 * <p><b>Date:</b> 2016年6月12日 下午8:59:41</p>
	 * @throws IOException
	 */
	@Test
	public void createIndexBySort() throws IOException {
		List<Document> docs = new ArrayList<Document>();
		FieldType type = new FieldType();
		type.setStored(true);//可以选择是否存储，默认为false
		type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);//默认为null
		type.setOmitNorms(false);//表示是否存储norms信息
		type.setTokenized(true);//如果设置为false，表示不被Analyze，默认为true
		Document doc = new Document();
		Field field = new Field("Name", "Leonardo DiCaprio", type);
		field.setBoost(2.0f);
		doc.add(field);
		doc.add(new Field("Value", "HollyWood King", type));
		docs.add(doc);
		doc = new Document();
		field = new Field("Name", "Tom Hanks", type);
		doc.add(field);
		doc.add(new Field("Value", "HollyWood Father", type));
		docs.add(doc);
		doc = new Document();
		field = new Field("Name", "Tom Cruise", type);
		field.setBoost(3.0f);
		doc.add(field);
		doc.add(new Field("Value", "HollyWood Sun", type));
		docs.add(doc);
		doc = new Document();
		doc.add(new Field("Name", "Jack", type));
		doc.add(new Field("Value", "Nice", type));
		docs.add(doc);
		doc = new Document();
		doc.add(new Field("Name", "Lucene", type));
		doc.add(new Field("Value", "Hello World!", type));
		doc.add(new Field("Value", "It's A Good Book!", type));
		doc.add(new Field("Value", "It's A Nice Technology!", type));
		docs.add(doc);
		writer.addDocuments(docs);
	}
	/**
	 * <p><b>Description:</b> 根据IndexReader进行简单的遍历，适用于数据量较小的情况</p>
	 * @throws IOException
	 */
	@Test
	public void searchAll() throws IOException {
		IndexReader reader = DirectoryReader.open(writer);
		//count获取的是曾经存放的doc总数，包括IndexWriter用delete方法删除的
		int count = reader.getDocCount("Name");
		System.out.println(count);
		//numDocs表示索引中可被索引的doc数，被删除的不算在内
		int numDocs = reader.numDocs();// 获取document的总数量
		System.out.println(numDocs);
		for (int i = 0; i < numDocs; i++) {
			Document doc = reader.document(i);
			/*List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.print(f.name() + "------" + f.stringValue() + "\t");
			System.out.println();*/
			Iterator<IndexableField> iterator = doc.iterator();
			while(iterator.hasNext()){
				IndexableField next = iterator.next();
				System.out.print(next.getClass()+"----"+next.name()+"----"+next.boost()+"----"+next.stringValue()+"----"+next.numericValue()+"\t");//boost()获取权值
			}
			System.out.println();
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> 简单的分词查询: 可用于模糊查找</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void queryParserTest() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		// 要经过一次QueryParser解析要查找的Field，并将Analyzer作为参数传给QueryParser
		QueryParser parser = new QueryParser("Value", analyzer);
		//设置默认的语句操作符
		parser.setDefaultOperator(Operator.OR);
		//表示允许通配符出现在Term的开头
		parser.setAllowLeadingWildcard(true);
		parser.setPhraseSlop(10);
		// StandardAnalyzer会自动将字母转为小写
		// 而QueryParser的parse方法同样可以实现将字母全部转为小写的功能，所以仍旧可以查的出
		Query query = parser.parse("Name:Lucene AND \"Hello World\"~5");
		// 如果parse中传的参数是一个词，则query的实际类型为TermQuery，反之，则为BooleanQuery
		System.out.println(query.getClass());
		TopDocs topDocs = searcher.search(query, 10);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.println(s + "-----" + f.name() + "------" + f.stringValue());
		}
		//词频查看
		int freq = reader.docFreq(new Term("Name","jack"));
		System.out.println(freq);
		reader.close();
	}
	/**
	 * <p><b>Description:</b> 简单的利用TermQuery查询: TermQuery只能以Term为单位查询，且对大小写敏感 <br/>
	 * 如果建索引时使用了StandardAnalyzer，则表示建立的索引中所有的大写字母全部转为小写字母 <br/>
	 * 如果用TermQuery，则只能查到小写的数据 <br/>
	 * 如果有多个词的话，想实现精确查找，可以用BooleanQuery将N个TermQuery拼在一起实现</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("resource")
	@Test
	public void booleanQueryTest() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		TokenStream tokenStream = new StandardAnalyzer().tokenStream("Name","Tom Hanks");
		// 查看最终从TokenStream中取出的Token信息
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			String asString = tokenStream.reflectAsString(false);
			System.out.println(asString);
		}
		tokenStream.end();
		// TermQuery query = new TermQuery(new Term("Name", "jack"));
		TermQuery query1 = new TermQuery(new Term("Name", "tom"));
		TermQuery query2 = new TermQuery(new Term("Value", "hollywood"));
		BooleanQuery query = new BooleanQuery.Builder().add(query1, Occur.MUST).add(query2, Occur.MUST).build();
		TopDocs topDocs = searcher.search(query, 1);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.println(s + "-----" + f.name() + "------" + f.stringValue());
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> TermRangeQuery的使用</p>
	 * <p><b>Since:</b> v1.0</p>
	 * <p><b>Date:</b> 2016年6月13日 下午11:58:43</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void termRangeQueryTest() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		TermRangeQuery query = new TermRangeQuery("Name",new BytesRef("lack"),new BytesRef("tom"),true,true);
		TopDocs topDocs = searcher.search(query, 10);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.print(s + "-----" + f.name() + "------" + f.stringValue()+"\t");
			System.out.println();
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> PointRangeQuery的使用</p>
	 * <p><b>Since:</b> v1.0</p>
	 * <p><b>Date:</b> 2016年6月14日 上午12:30:30</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void pointRangeQueryTest() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = IntPoint.newRangeQuery("Age", 0, 15);
		System.out.println(query.getClass());
		TopDocs topDocs = searcher.search(query, 10);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.print(s + "-----" + f.name() + "------" + f.stringValue()+"\t");
			System.out.println();
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> PrefixQuery的使用: 同样没有实现将单词转为小写</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void prefixQueryTest() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		PrefixQuery query = new PrefixQuery(new Term("Value","holly"));
		TopDocs topDocs = searcher.search(query, 10);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.print(s + "-----" + f.name() + "------" + f.stringValue()+"\t");
			System.out.println();
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> PhraseQuery的使用: 实现短语间距离查询</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void phraseQueryTest() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		PhraseQuery query=new PhraseQuery(40,"Value",new BytesRef("good"),new BytesRef("book"));
		TopDocs topDocs = searcher.search(query, 10);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.print(s + "-----" + f.name() + "------" + f.stringValue()+"\t");
			System.out.println();
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> MatchAllDosQuery的使用: 查询出所有索引文件</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void matchAllDocsQueryTest() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query=new MatchAllDocsQuery();
		TopDocs topDocs = searcher.search(query, 10);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.print(s + "-----" + f.name() + "------" + f.stringValue()+"\t");
			System.out.println();
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> 按照查询结果分数倒排: TopScoreDocCollector</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void searchByCollector() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		// 要经过一次QueryParser解析要查找的Field，并将Analyzer作为参数传给QueryParser
		QueryParser parser = new QueryParser("Name", new StandardAnalyzer());
		Query query = parser.parse("Tom");
		// TopScoreDocCollector是Collector的实现类，以TopDocs的形式返回，用于基于TopDocs的搜索
		// 同时，结果会按照score倒排，创建Collector实例时，要事先知道documents是否是以docId的顺序收集的
		TopScoreDocCollector collector = TopScoreDocCollector.create(10);// 参数是控制返回个数的
		searcher.search(query, collector);
		TopDocs topDocs = collector.topDocs();// 注意其返回不是靠IndexSearcher类返回的
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.println(s + "-----" + f.name() + "------" + f.stringValue());
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> 多属性域查询，通过操作运算符实现: MultiFieldQueryParser</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void searchByMultiFieldSearcher() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = MultiFieldQueryParser.parse(new String[] { "Tom","HollyWood" }, new String[] { "Name", "Value" }, new Occur[] {Occur.MUST, Occur.MUST }, new StandardAnalyzer());
		TopDocs topDocs = searcher.search(query, 10);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.println(s + "-----" + f.name() + "------" + f.stringValue());
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> 模糊查询: FuzzyQuery<br/>
	 * 会索引出所有相似的结果，同时也没有实现大小写转换</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void fuzzyQueryTest() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = new FuzzyQuery(new Term("Name", "tam"));
		TopDocs topDocs = searcher.search(query, 10);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.print(s + "-----" + f.name() + "------" + f.stringValue()+"\t");
			System.out.println();
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> 通配符查询: "\"是转义字符，"*"匹配任意多个字符，包括空字符，"?"匹配0-1个字符 <br/>
	 * 由于迭代了很多Term，因此效率很低，为避免效率过于低下，避免在开头使用"*" <br/>
	 * 注意：通配符查询同样没有没有实现忽略大小写，所以经过StandardAnalyzer转换的单词，只能通过小写方式获取</p>
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void searchByWildcardSearcher() throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(writer);
		IndexSearcher searcher = new IndexSearcher(reader);
		// 要经过一次QueryParser解析要查找的Field，并将Analyzer作为参数传给QueryParser
		WildcardQuery query = new WildcardQuery(new Term("Name", "*om*"));
		TopDocs topDocs = searcher.search(query, 5);
		ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			float s = docs[i].score;
			Document doc = searcher.doc(docs[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField f : fields)
				System.out.println(s + "-----" + f.name() + "------" + f.stringValue());
		}
		reader.close();
	}
	/**
	 * <p><b>Description:</b> <i>高亮显示示例</i> <br/>
	 * 对于文本量很大的文档等，使用FastVectorHighlighter要比直接使用Highlighter效率要高 <br/>
	 * 但FastVectorHighlighter类不支持WildcardQuery和SpanQuery查询，同时会消耗磁盘空间</p>
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	@SuppressWarnings("resource")
	@Test
	public void testHighlight() throws IOException,InvalidTokenOffsetsException {
		// demo为测试字符文本
		String demo = "Hello World! This is my first Highlighter demo.";
		Query query = new FuzzyQuery(new Term("Test", "World"));
		QueryScorer scorer = new QueryScorer(query);
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color='pink'>", "</font>");
		// 1、创建Highlighter对象：Highlighter对象需要传入Formatter和Scorer参数
		Highlighter highlighter = new Highlighter(formatter, scorer);
		// 2、利用分词器处理成TokenStream
		TokenStream tokenStream = new StandardAnalyzer().tokenStream("Test",demo);
		// 3、利用Highlighter获取高亮文本输出
		// 注意：如果直接使用分析的文本要与分词器给定的文本一致
		String testHighlighter = highlighter.getBestFragment(tokenStream, demo);
		System.out.println(testHighlighter);
		String test2Text = "I Love The World! I am a human";
		// 目的是将原始字符串拆分成独立的片段
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
		String fragment = highlighter.getBestFragment(new StandardAnalyzer().tokenStream("Demo", test2Text),test2Text);
		System.out.println(fragment);
	}
	/**
	 * <p><b>Description:</b> 更新操作: 底层是调用了删除操作和添加操作<br/>
	 * 如果找到匹配的就删除，有可能会删除多个，如果没找到就直接执行添加动作</p>
	 * @throws IOException
	 */
	@Test
	public void update() throws IOException{
		Document doc=new Document();
		FieldType type = new FieldType();
		type.setIndexOptions(IndexOptions.DOCS_AND_FREQS);//默认为空
		type.setStored(true);//默认为false
		doc.add(new Field("Name","Shakira",type));
		doc.add(new Field("Value","Wonderful",type));
		writer.updateDocument(new Term("Name","test"), doc);
		writer.forceMergeDeletes();
	}
	/**
	 * <p><b>Description:</b> 索引文件的删除: 使用IndexWriter删除<br/>
	 * 调用commit()或close()会提交更改，但空间不会马上释放，而是标记为"删除"</p>
	 * @throws IOException
	 */
	@Test
	public void testIndexWriterDelete() throws IOException{
		//删除指定的document: 删除后不会马上生效，而是会被缓存，只有写入到索引文件中，reader再次打开才生效
		//该方式删除后，仍然会被检索到
		writer.deleteDocuments(new Term("Name","test"));
		//即使IndexWriter在删除后进行提交（commit操作），也不会对删除动作马上生效
		//commit()操作和flush()操作暂时不知道有什么作用??????????????????????????????
		writer.commit();
		writer.flush();
		writer.forceMergeDeletes();//若要使删除的立即生效，则需要调用该方法，否则的话，总会将被删除的写入缓存
		boolean hasDeletions = writer.hasDeletions();//用法同reader的一样
		System.out.println(hasDeletions);
		//删除全部: 该删除会马上生效
		writer.deleteAll();
	}
	/**
	 * <p><b>Description:</b> IndexReader查询删除状态</p>
	 * @throws IOException
	 */
	@Test
	public void testIndexReaderConfirm() throws IOException{
		IndexReader reader=DirectoryReader.open(writer);
		boolean hasDeletions = reader.hasDeletions();
		System.out.println(hasDeletions);
		int num = reader.numDeletedDocs();
		System.out.println(num);
		reader.close();
	}
	/**
	 * <p><b>Description:</b> 操作完之后要对IndexWriter执行关流操作</p>
	 * @throws IOException
	 */
	@After
	public void after() throws IOException {
		writer.close();
	}
}
