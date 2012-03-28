package com.isoco.xlike.components.jsi;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import com.isoco.xlike.util.decompressFile;

public class jsiNews extends WebPage {
	private static final long serialVersionUID = 1L;
	private MultiLineLabel label;

	private TextField txt_q;
	private TextField txt_qt;
	private TextField txt_qb;
	private TextField txt_lang;
	private TextField txt_date;
	private TextField txt_offset;

	public jsiNews(final PageParameters parameters) {

		Form<?> form = new Form<Void>("form_news");

		txt_q = new TextField("txt_q", new Model(""));
		txt_qt = new TextField("txt_qt", new Model(""));
		txt_qb = new TextField("txt_qb", new Model(""));
		txt_lang = new TextField("txt_lang", new Model(""));
		txt_date = new TextField("txt_date", new Model(""));
		txt_offset = new TextField("txt_offset", new Model(""));

		form.add(txt_q);
		form.add(txt_qt);
		form.add(txt_qb);
		form.add(txt_lang);
		form.add(txt_date);
		form.add(txt_offset);

		form.add(new Button("button_txt") {
			@Override
			public void onSubmit() {
				String value = new decompressFile().jsiDecompressTXT();
				label.setDefaultModelObject(value);
			}
		});
		form.add(new Button("button_query") {
			@Override
			public void onSubmit() {
				
				  StringBuffer value = new newsParser().jsiNews( (String)
				  txt_q.getModelObject(), (String) txt_qt.getModelObject(),
				  (String) txt_qb.getModelObject(), (String)
				  txt_lang.getModelObject(), (String) txt_date.getModelObject(),(String) txt_offset.getModelObject());
				 
				
				/*
				System.out.println("q: " + (String) txt_q.getModelObject()
						+ " - qt: " + (String) txt_qt.getModelObject()
						+ " - qb: " + (String) txt_qb.getModelObject()
						+ " - lang: " + (String) txt_lang.getModelObject());
						*/
				
				/*StringBuffer value = new newsParser()
						.jsiNews("", "", "", "eng", );*/
				label.setDefaultModelObject(value);
			}
		});
		add(form);
		//add((label = new MultiLineLabel("message", new Model(""))));
		add((label = new MultiLineLabel("message", new Model("")))
		 .setEscapeModelStrings(false));
	}
}
