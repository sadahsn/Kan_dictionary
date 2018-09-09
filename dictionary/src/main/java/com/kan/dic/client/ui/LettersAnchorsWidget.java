package com.kan.dic.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.kan.dic.client.ui.SuggestBoxWidget.MySuggestOracle;

public class LettersAnchorsWidget {

	private HorizontalPanel panel;
	public LettersAnchorsWidget() {
		initVerticalPanel();
	}

	private void initVerticalPanel() {
		panel = new HorizontalPanel();
		panel.setSpacing(10);
	}
	
	public HorizontalPanel getAnchorPanel(){
		sendReuest();
		return panel;
	}
	
	private void sendReuest(){
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "dictionary/getAllLetters");
		builder.setCallback(new RequestCallback() {
			
			public void onResponseReceived(Request arg0, Response resp) {
				System.out.println(resp.getText());
				String[] letters = resp.getText().split(",");
				for(String letter: letters){
					final Anchor letterAnchor = new Anchor(letter);
					letterAnchor.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent arg0) {
							// TODO Auto-generated method stub
//							Window.alert(letterAnchor.getText());
							setRequestToGetMeanings(letterAnchor.getText());
							
						}
					});
					panel.add(letterAnchor);
				}
			}
			
			public void onError(Request arg0, Throwable arg1) {
			
			}
		});
		builder.setHeader("Content-Type","application/x-www-form-urlencoded");
		try {
			builder.send();
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setRequestToGetMeanings(String textToServer) {RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "dictionary/getLocalSuggestList?input="+textToServer);
	builder.setCallback(new RequestCallback() {
		
		public void onResponseReceived(Request arg0, Response resp) {
			String[] words = resp.getText().split(",");
			MeaningLabelWidget.get().setText(resp.getText());
		}
		
		public void onError(Request arg0, Throwable arg1) {
		
		}
	});
	builder.setHeader("Content-Type","application/x-www-form-urlencoded");
	builder.setRequestData("input="+textToServer);
	
	try {
		builder.send();
	} catch (RequestException e) {
		e.printStackTrace();
	}
}


	
}
