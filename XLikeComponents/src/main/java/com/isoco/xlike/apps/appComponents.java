package com.isoco.xlike.apps;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import com.isoco.xlike.components.jsi.jsiNews;
import com.isoco.xlike.components.rss.Rss;
import com.isoco.xlike.pages.index.index;

public class appComponents extends WebApplication {

	
	@Override
	protected void init(){
		mountBookmarkablePage("/index", index.class);
		mountBookmarkablePage("/news", jsiNews.class);
		//mountBookmarkablePage();
	}
	@Override
	public Class<? extends Page> getHomePage() {
		return index.class; //return default page
	}

}
