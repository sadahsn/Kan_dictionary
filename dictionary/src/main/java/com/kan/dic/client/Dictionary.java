package com.kan.dic.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.kan.dic.client.ui.LettersAnchorsWidget;
import com.kan.dic.client.ui.MeaningLabelWidget;
import com.kan.dic.client.ui.SuggestBoxWidget;
import com.kan.dic.shared.FieldVerifier;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Dictionary implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("ಹುಡುಕು");
		final Label errorLabel = new Label();
		LettersAnchorsWidget anchors = new LettersAnchorsWidget();
		final SuggestBoxWidget suggestBox = new SuggestBoxWidget();
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		// RootPanel.get("nameFieldContainer").add(suggestBox);
		RootPanel.get("nameFieldContainer").add(suggestBox.getSuggestBox());
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("anchorTagsContainer").add(anchors.getAnchorPanel());
		RootPanel.get("meaningContainer").add(MeaningLabelWidget.get());
		

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendRequestToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendRequestToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendRequestToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = suggestBox.getSuggestBox().getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least one characters");
					return;
				}

				// Then, we send the input to the server.
				RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "dictionary/getWordMeaning?input="+textToServer);
				builder.setCallback(new RequestCallback() {
					
					public void onResponseReceived(Request arg0, Response resp) {
						Window.alert(resp.getText());
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
//		suggestBox.getSuggestBox().addKeyUpHandler(handler);
	}

}
