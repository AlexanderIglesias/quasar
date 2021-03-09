package com.mercadolibre.challenge.quasar.data.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MessageUtils {

	public static final  String WOOKIE_PROBLEM = "raaaaaahhgh huurh aguhwwgggghhh raaaaaahhgh";//We have a Problem!
	public static final  String WOOKIE_NOT_SAT = "uughguughhhghghghhhgh raaaaaahhgh uuh huuguughghg";//Don't Find Sat
	public static final  String WOOKIE_OK = "aarrragghuuhw";//OK!

	public List<String[]> iterateToRemove(List<String[]> messages) {
		int minimumSize = 0;
		List<String[]> listAux = messages;

		minimumSize = getMinimumSize(messages, minimumSize);

		for (String[] msg : listAux) {
			int positionRemove = msg.length - minimumSize;
			if (positionRemove != 0) {
				String[] msgAux = Arrays.copyOfRange(msg, positionRemove, msg.length);
				messages.set(listAux.indexOf(msg), msgAux);
			}
		}
		return messages;
	}

	public int getMinimumSize(List<String[]> messages, int minimumSize) {
		for (String[] msg : messages) {
			if (minimumSize > msg.length || minimumSize == 0) {
				minimumSize = msg.length;
			}
		}
		return minimumSize;
	}

	public List<String> getWords(List<String> completeMessage, String[] msg) {
		if (completeMessage.isEmpty()) {
			completeMessage = Arrays.asList(msg);
			return completeMessage;
		}

		for (int i = 0; i < msg.length; i++) {
			if (msg[i].isEmpty()) {
				continue;
			}

			if (!completeMessage.contains(msg[i]) && completeMessage.get(i).isEmpty()) {
				completeMessage.set(i, msg[i]);
			}
		}

		return completeMessage;

	}

	public String formatMessage(List<String> completeMessage) {
		List<String> msgAux = new ArrayList<>();
		msgAux.addAll(completeMessage);
		for (String word : completeMessage) {
			if (word.isEmpty()) {
				msgAux.remove(completeMessage.indexOf(word));
			} else if (!word.isEmpty()) {
				break;
			}
		}
		return msgAux.toString().replace("[", "").replace(",", "").replace("]", "");
	}

}
