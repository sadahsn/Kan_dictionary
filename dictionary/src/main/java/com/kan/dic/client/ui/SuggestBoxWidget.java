package com.kan.dic.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

public class SuggestBoxWidget extends Widget {

	private SuggestBox suggestBox;
	private MySuggestOracle suggestOracle;

	public SuggestBoxWidget() {
		super();
		createSuggestOracle();
		createSuggestBox();
		setElement(suggestBox.getElement());
	}

	private void createSuggestOracle() {
		suggestOracle = new MySuggestOracle();
	}

	private void createSuggestBox() {
		suggestBox = new SuggestBox(suggestOracle);
		suggestBox.addKeyUpHandler(new SuggestHandler());
	}

	public SuggestBox getSuggestBox() {
		return suggestBox;
	}
	
	class SuggestHandler implements KeyUpHandler{

		public void onKeyUp(KeyUpEvent evnt) {
			RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "dictionary/getLocalSuggestList?input="+suggestBox.getValue());
			builder.setCallback(new RequestCallback() {
				
				public void onResponseReceived(Request arg0, Response resp) {
					String[] suggestions = resp.getText().split(",");
					final List<Suggestion> suggestionsList = new ArrayList<Suggestion>();
					for(final String suggestion: suggestions){
						Suggestion suggString = new Suggestion() {
							
							public String getReplacementString() {
								return suggestion;
							}
							
							public String getDisplayString() {
								return suggestion;
							}
						};
						suggestionsList.add(suggString);
					}
					((MySuggestOracle)suggestBox.getSuggestOracle()).setSuggestions(suggestionsList);
				}
				
				public void onError(Request arg0, Throwable arg1) {
				
				}
			});
			builder.setHeader("Content-Type","application/x-www-form-urlencoded");
			builder.setRequestData("input="+suggestBox.getValue());
			
			try {
				builder.send();
			} catch (RequestException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	class MySuggestOracle extends SuggestOracle {
		
		private List<Suggestion> suggestions;

		@Override
		public void requestSuggestions(Request req, Callback callback) {
			callback.onSuggestionsReady(req, new Response(suggestions));
		}
		
		public List<Suggestion> getSuggestions(){
			return suggestions;
		}
		
		public void setSuggestions(List<Suggestion> suggestions){
			this.suggestions = suggestions;
		}
		
	}
	
}
