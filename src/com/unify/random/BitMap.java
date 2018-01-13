package com.unify.random;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BitMap {

	class TestCanvas extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (int i = 0; i < 128; i++) {
				for (int j = 0; j < 128; j++) {
					int x = 3 * (i + 1);
					int y = 3 * (j + 1);
					int R = rgb[i][j][0];
					int G = rgb[i][j][1];
					int B = rgb[i][j][2];
					g.setColor(new Color(R, G, B));
					g.fillRect(x, y, 5, 5);
				}
			}
		}
	}

	private static final String test = "https://www.random.org/integers/?num=384&min=1&max=256&col=1&base=10&format=plain&rnd=new";
	int rgb[][][] = new int[128][128][3];

	public int[] getRandomArray() throws Exception {
		URL url = new URL(test);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String input;
		int arr[] = new int[384];
		int i = 0;
		while ((input = in.readLine()) != null) {
			arr[i++] = Integer.parseInt(input) - 1;
		}
		return arr;
	}

	public BitMap() throws Exception {
		for (int i = 0; i < 128; i++) {
			for (int j = 0; j < 3; j++) {
				int arr[] = getRandomArray();
				for (int k = 0; k < 128; k++)
					rgb[i][k][0] = arr[k];
				for (int k = 0; k < 128; k++)
					rgb[i][k][1] = arr[k + 128];
				for (int k = 0; k < 128; k++)
					rgb[i][k][2] = arr[k + 256];
			}
		}

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 400, 400);
		frame.getContentPane().add(new TestCanvas());
		frame.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		new BitMap();
	}

}
