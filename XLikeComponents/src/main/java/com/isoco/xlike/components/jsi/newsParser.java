package com.isoco.xlike.components.jsi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.isoco.xlike.components.rss.RssParser.Item;

/**
 * 
 * @author rcharro
 * 
 */
public class newsParser extends DefaultHandler {

	private StringBuffer buffer_news = new StringBuffer();
	private TagsNews tagsNews;
	private newArticle article;
	private String urlString;
	private StringBuilder text;

	/**
	 * 
	 * @author rcharro
	 * 
	 */
	public StringBuffer jsiNews(String txt_q, String txt_qt, String txt_qb,
			String txt_lang, String txt_date, String txt_offset) {

		try {

			if (txt_q == null && txt_qt == null && txt_qb == null
					&& txt_lang == null && txt_date == null
					&& txt_offset == null) {
				return null;
			}

			if (txt_q == null) {
				txt_q = "";
			}
			if (txt_qt == null) {
				txt_qt = "";
			}
			if (txt_qb == null) {
				txt_qb = "";
			}
			if (txt_lang == null) {
				txt_lang = "";
			}
			if (txt_date == null) {
				txt_date = "";
			}
			if (txt_offset == null) {
				txt_offset = "";
			}

			String query_url = "/query/news-search?q=" + txt_q + "&qt="
					+ txt_qt + "&qb=" + txt_qb + "&lang=" + txt_lang + "&date="
					+ txt_date + "&offset=" + txt_offset;

			URL url = new URL("http://newsfeed.ijs.si" + query_url);

			this.urlString = url.toString();
			this.text = new StringBuilder();
			/*
			 * BufferedReader in = new BufferedReader(new InputStreamReader(
			 * url.openStream()));
			 * 
			 * String inputLine;
			 * 
			 * while ((inputLine = in.readLine()) != null) {
			 * 
			 * // System.out.println(inputLine);
			 * 
			 * buffer_news.insert(buffer_news.length(), inputLine); }
			 * 
			 * in.close();
			 */

			parse();

			TagsNews newsArticles = getNews();

			buffer_news.insert(buffer_news.length(),
					"<b>" + newsArticles.items.size() + " News - List: </b>");

			for (int i = 0; i < newsArticles.items.size(); i++) {

				// Article: Title
				buffer_news.insert(buffer_news.length(), "\n\n<b>" + (i + 1)
						+ ". Title - </b>"
						+ newsArticles.items.get(i).article_title);

				// Article: PubDate
				buffer_news.insert(buffer_news.length(), "\n<b>PubDate - </b>"
						+ newsArticles.items.get(i).article_date);

				// Article: Language
				buffer_news.insert(buffer_news.length(), "\n<b>Language - </b>"
						+ newsArticles.items.get(i).article_lang);

				// Article: URI
				buffer_news.insert(buffer_news.length(), "\n<b>URI - </b>"
						+ newsArticles.items.get(i).article_uri);

				// Article: Description
				buffer_news.insert(buffer_news.length(),
						"\n<b>Description - </b>"
								+ newsArticles.items.get(i).article_body);

				// Article: Source - Title
				buffer_news.insert(
						buffer_news.length(),
						"\n<b>Source - Title - </b>"
								+ newsArticles.items.get(i).source_title);

				// Article: Source - URI
				buffer_news.insert(buffer_news.length(),
						"\n<b>Source - URI - </b>"
								+ newsArticles.items.get(i).source_uri);

				// Article: Source - Type
				buffer_news.insert(buffer_news.length(),
						"\n<b>Source - Type - </b>"
								+ newsArticles.items.get(i).source_type);

			}

			return buffer_news;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return null;
		}

	}

	public void parse() {

		InputStream urlInputStream = null;
		SAXParserFactory spf = null;
		SAXParser sp = null;

		try {
			URL url = new URL(this.urlString);
			_setProxy(); // Set the proxy if needed
			urlInputStream = url.openConnection().getInputStream();
			spf = SAXParserFactory.newInstance();
			if (spf != null) {
				sp = spf.newSAXParser();
				sp.parse(urlInputStream, this);
			}
		}
		/*
		 * Exceptions need to be handled MalformedURLException
		 * ParserConfigurationException IOException SAXException
		 */

		catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		} finally {
			try {
				if (urlInputStream != null)
					urlInputStream.close();
			} catch (Exception e) {
			}
		}

	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {
		if (qName.equalsIgnoreCase("results")) {
			// System.out.println("Ok - Results");
			this.tagsNews = new TagsNews();
		} else if (qName.equalsIgnoreCase("article") && (this.tagsNews != null)) {
			// System.out.println("Ok - Article");
			this.article = new newArticle();
			this.tagsNews.addArticle(this.article);
		}
	}

	public void endElement(String uri, String localName, String qName) {
		if (this.tagsNews == null)
			return;

		if (qName.equalsIgnoreCase("article"))
			this.article = null;

		else if (qName.equalsIgnoreCase("source-uri")) {
			// System.out.println("Source-uri" + this.text.toString());
			this.article.source_uri = this.text.toString().trim();
		}

		else if (qName.equalsIgnoreCase("source-title")) {
			this.article.source_title = this.text.toString().trim();
		}

		else if (qName.equalsIgnoreCase("source-type")) {
			this.article.source_type = this.text.toString().trim();
		}

		else if (qName.equalsIgnoreCase("article-title")) {
			this.article.article_title = this.text.toString().trim();
		}

		else if (qName.equalsIgnoreCase("article-lang")) {
			this.article.article_lang = this.text.toString().trim();
		}

		else if (qName.equalsIgnoreCase("article-uri")) {
			this.article.article_uri = this.text.toString().trim();
		}

		else if (qName.equalsIgnoreCase("article-body")) {
			this.article.article_body = this.text.toString().trim();
		}

		else if (qName.equalsIgnoreCase("article-date")) {
			this.article.article_date = this.text.toString().trim();
		}

		this.text.setLength(0);
	}

	public void characters(char[] ch, int start, int length) {
		this.text.append(ch, start, length);
	}

	public static void _setProxy() throws IOException {
		Properties sysProperties = System.getProperties();
		sysProperties.put("proxyHost", "<Proxy IP Address>");
		sysProperties.put("proxyPort", "<Proxy Port Number>");
		System.setProperties(sysProperties);
	}

	public TagsNews getNews() {
		return (this.tagsNews);
	}

	public class TagsNews {

		private double results_hits;

		private ArrayList<newArticle> items;

		public void addArticle(newArticle item) {
			if (this.items == null)
				this.items = new ArrayList<newArticle>();
			this.items.add(item);
		}

	}

	public class newArticle {
		private double article_id;
		private double article_rank; // Asociado al orden que aparece en el XML
										// de la Consulta realizada

		private String source_uri;
		private String source_title;
		private String source_type;

		private String article_lang;
		private String article_uri;
		private String article_date;
		private String article_title;
		private String article_body;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(new newsParser().jsiNews("", "", "", "eng", "", ""));
	}

}
