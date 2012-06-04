package com.gmail.at.zhuikov.aleksandr.client;

import java.util.List;

import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.ui.client.adapters.ValueBoxEditor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ValueBoxBase;

public class EditorErrorDecorator<T> implements HasEditorErrors<T>,
		IsEditor<ValueBoxEditor<T>> {

	private Label errorLabel;
	private ValueBoxBase<T> widget;

	public EditorErrorDecorator(ValueBoxBase<T> widget, Label errorLabel) {
		this.widget = widget;
		this.errorLabel = errorLabel;
	}

	@Override
	public void showErrors(List<EditorError> errors) {

		StringBuilder message = new StringBuilder();
		
		for (EditorError error : errors) {
			if (error.getEditor().equals(this)) {
				message.append(error.getMessage());
				message.append("<br/>");
			}
		}
		
		if (message.length() == 0) {
			errorLabel.setText("");
			errorLabel.addStyleName("hidden");
			return;
		}

		errorLabel.getElement().setInnerHTML(message.toString());
		errorLabel.removeStyleName("hidden");
	}

	@Override
	public ValueBoxEditor<T> asEditor() {
		return widget.asEditor();
	}
}
