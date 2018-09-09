package com.kan.dic.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DictionaryServiceAsync {
	public void getLocalSuggestList(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
