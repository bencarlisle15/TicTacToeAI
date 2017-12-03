import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

	public NumberTextField(int number) {
		setText(String.valueOf(number));
	}

	@Override
	public void replaceText(int start, int end, String text) {
		String newText = getText().substring(0, start) + text + getText().substring(end);
		if (newText.matches("[0-9]*")) {
			super.replaceText(start, end, text);
		}
	}
}