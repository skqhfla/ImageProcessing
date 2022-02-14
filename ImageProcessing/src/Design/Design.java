package Design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Design extends JFrame implements ActionListener {

	public static final Dimension DIM_Panel = new Dimension(500, 500);
	public static final Dimension DIM_BUTTON_Panel = new Dimension(50, 100);
	public static final Dimension DIM_BUTTON = new Dimension(50, 50);

	private JPanel contentPane;
	private ImagePanel panel_before;

	public ImagePanel getPanel_before() {
		return panel_before;
	}

	private ImagePanel panel_after;

	public ImagePanel getPanel_after() {
		return panel_after;
	}

	private JPanel panel_button;
	private BufferedImage bufferedImage;

	private int width = 1300;
	private int height = 600;

	public Design() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setSize(width, height);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu Menu_file = new JMenu("File");
		menuBar.add(Menu_file);

		JMenuItem Item_open = new JMenuItem("열기");
		Menu_file.add(Item_open);

		JMenuItem Item_save = new JMenuItem("저장하기");
		Menu_file.add(Item_save);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 20, 500, 20, 500, 20, 100, 20, 0 };
		gbl_contentPane.rowHeights = new int[] { 20, 600, 20, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		panel_before = new ImagePanel();
		panel_before.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_before = new GridBagConstraints();
		gbc_panel_before.insets = new Insets(0, 0, 5, 5);
		gbc_panel_before.fill = GridBagConstraints.BOTH;
		gbc_panel_before.gridx = 1;
		gbc_panel_before.gridy = 1;
		contentPane.add(panel_before, gbc_panel_before);
		panel_before.setSize(DIM_Panel);

		panel_after = new ImagePanel();
		panel_after.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_after = new GridBagConstraints();
		gbc_panel_after.insets = new Insets(0, 0, 5, 5);
		gbc_panel_after.fill = GridBagConstraints.BOTH;
		gbc_panel_after.gridx = 3;
		gbc_panel_after.gridy = 1;
		contentPane.add(panel_after, gbc_panel_after);
		panel_after.setSize(DIM_Panel);

		panel_button = new JPanel();
		panel_button.setBackground(Color.GRAY);
		panel_button.setSize(DIM_BUTTON_Panel);
		GridBagConstraints gbc_panel_button = new GridBagConstraints();
		gbc_panel_button.insets = new Insets(0, 0, 5, 5);
		gbc_panel_button.fill = GridBagConstraints.BOTH;
		gbc_panel_button.gridx = 5;
		gbc_panel_button.gridy = 1;
		contentPane.add(panel_button, gbc_panel_button);
		GridBagLayout gbl_panel_button = new GridBagLayout();
		gbl_panel_button.columnWidths = new int[] { 100, 0 };
		gbl_panel_button.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_button.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_button.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_button.setLayout(gbl_panel_button);

		JButton button_black = new JButton("흑백");
		GridBagConstraints gbc_button_black = new GridBagConstraints();
		gbc_button_black.insets = new Insets(0, 0, 5, 0);
		gbc_button_black.gridx = 0;
		gbc_button_black.gridy = 0;
		panel_button.add(button_black, gbc_button_black);
		button_black.setSize(DIM_BUTTON);

		JButton button_edge = new JButton("Edge");
		GridBagConstraints gbc_button_edge = new GridBagConstraints();
		gbc_button_edge.insets = new Insets(0, 0, 5, 0);
		gbc_button_edge.gridx = 0;
		gbc_button_edge.gridy = 2;
		panel_button.add(button_edge, gbc_button_edge);
		button_edge.setSize(DIM_BUTTON);

		JLabel Label_bright = new JLabel("밝기");
		GridBagConstraints gbc_Label_bright = new GridBagConstraints();
		gbc_Label_bright.insets = new Insets(0, 0, 5, 0);
		gbc_Label_bright.gridx = 0;
		gbc_Label_bright.gridy = 4;
		panel_button.add(Label_bright, gbc_Label_bright);

		JSlider slider_bright = new JSlider();
		slider_bright.setBackground(Color.GRAY);
		GridBagConstraints gbc_slider_bright = new GridBagConstraints();
		gbc_slider_bright.insets = new Insets(0, 0, 5, 0);
		gbc_slider_bright.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider_bright.gridx = 0;
		gbc_slider_bright.gridy = 5;
		panel_button.add(slider_bright, gbc_slider_bright);
		slider_bright.setSize(100, 30);

		JButton button_clear = new JButton("원본");
		GridBagConstraints gbc_button_clear = new GridBagConstraints();
		gbc_button_clear.gridx = 0;
		gbc_button_clear.gridy = 7;
		panel_button.add(button_clear, gbc_button_clear);

		Item_open.addActionListener(this);
		Item_save.addActionListener(this);
		button_black.addActionListener(this);
		button_edge.addActionListener(this);
		button_clear.addActionListener(this);
		slider_bright.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int bright = (int) source.getValue();
					BufferedImage changedImage = deepCopy(briconChange(bright));
					panel_after.setImage(changedImage);
				}
			}
		});
	}

	public class ImagePanel extends JPanel {
		Image image;

		public Image getImage() {
			return image;
		}

		Toolkit toolkit = getToolkit();

		public void setPath(String path) throws IOException {
			// image = toolkit.getImage(path);
			File input = new File(path);
			bufferedImage = ImageIO.read(input);
			image = bufferedImage.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
			panel_before.setSize(new Dimension(image.getWidth(null), image.getWidth(null)));
			panel_after.setSize(new Dimension(image.getWidth(null), image.getWidth(null)));
			panel_after.setImage(null);
		}

		public void setImage(Image newImage) {
			image = newImage;
		}

		public void paint(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			if (image != null)
				g.drawImage(image, 0, 0, this);
			this.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("열기")) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG&GIF Images", "jpg", "gif", "jpeg");
			chooser.setFileFilter(filter);

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;

			}
			System.out.println("path = " + chooser.getSelectedFile().getPath());
			try {
				panel_before.setPath(chooser.getSelectedFile().getPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("흑백")) {
			Image image = panel_before.getImage();
			System.out.println("width = " + image.getWidth(null) + " height = " + image.getHeight(null));
			BufferedImage copyImage = deepCopy(bufferedImage);
			for (int y = 0; y < copyImage.getHeight(); y++) {
				for (int x = 0; x < copyImage.getWidth(); x++) {
					Color colour = new Color(copyImage.getRGB(x, y));
					int Y = (int) (0.2126 * colour.getRed() + 0.7152 * colour.getGreen() + 0.0722 * colour.getBlue());
					copyImage.setRGB(x, y, new Color(Y, Y, Y).getRGB());
				}
			}
			panel_after.setImage(copyImage);
		} else if (e.getActionCommand().equals("Edge")) {
			BufferedImage copyImage = deepCopy(bufferedImage);
			float[] sharpen = new float[] { 0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f };
			Kernel kernel = new Kernel(3, 3, sharpen);
			ConvolveOp op = new ConvolveOp(kernel);
			copyImage = op.filter(copyImage, null);
			double[][] ar = toArray(copyImage);
			double[][] filterBlur = { { 0.088, 0.107, 0.088 }, { 0.107, 0.214, 0.107 }, { 0.088, 0.107, 0.088 } };
			ar = convolution(ar, filterBlur);
			double[][] filterEdge = { { -1, -1, -1 }, { -1, 8, -1 }, { -1, -1, -1 } };
			ar = convolution(ar, filterEdge);
			ar = arrayInColorBound(ar);
			ar = arrayColorInverse(ar);
			copyImage = deepCopy(toImage(ar));

			panel_after.setImage(copyImage);
		} else if (e.getActionCommand().equals("원본")) {
			BufferedImage copyImage = bufferedImage;
			
			panel_after.setImage(copyImage);
		}
	}

	private BufferedImage toImage(double[][] ar) {
		BufferedImage output = new BufferedImage(ar[0].length, ar.length, BufferedImage.TYPE_INT_BGR);
		for (int y = 0; y < ar.length; y++) {
			for (int x = 0; x < ar[0].length; x++) {
				output.setRGB(x, y, new Color((int) ar[y][x], (int) ar[y][x], (int) ar[y][x]).getRGB());
			}
		}
		return output;
	}

	private double[][] arrayColorInverse(double[][] ar) {
		for (int i = 0; i < ar.length; i++) {
			for (int j = 0; j < ar[i].length; j++) {
				ar[i][j] = 255 - ar[i][j];
			}
		}
		return ar;
	}

	private double[][] arrayInColorBound(double[][] ar) {
		for (int i = 0; i < ar.length; i++) {
			for (int j = 0; j < ar[i].length; j++) {
				ar[i][j] = Math.max(0, ar[i][j]);
				ar[i][j] = Math.min(225, ar[i][j]);
			}
		}
		return ar;
	}

	private double[][] convolution(double map[][], double[][] filter) {
		double output[][] = new double[map.length][map[0].length];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				for (int i = 0; i < filter.length; i++) {
					for (int j = 0; j < filter[i].length; j++) {
						try {
							output[y][x] += map[y - i + 1][x - j + 1] * filter[i][j];
						} catch (ArrayIndexOutOfBoundsException e) {
						}
					}
				}
			}
		}
		return output;
	}

	private double[][] toArray(BufferedImage bi) {
		double[][] output = new double[bi.getHeight()][bi.getWidth()];
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth(); x++) {
				Color c = new Color(bi.getRGB(x, y));
				output[y][x] = (c.getRed() + c.getBlue() + c.getGreen()) / 3;
			}
		}
		return output;
	}

	private static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public BufferedImage briconChange(int bright) {
		Image image = panel_after.getImage();
		int red[][] = new int[image.getHeight(null)][image.getWidth(null)];
		int blue[][] = new int[image.getHeight(null)][image.getWidth(null)];
		int green[][] = new int[image.getHeight(null)][image.getWidth(null)];

		BufferedImage tempImage = deepCopy(bufferedImage);

		for (int y = 0; y < image.getHeight(null); y++) {
			for (int x = 0; x < image.getWidth(null); x++) {
				Color c = new Color(tempImage.getRGB(x, y));

				red[y][x] = c.getRed() + bright;
				blue[y][x] = c.getBlue() + bright;
				green[y][x] = c.getGreen() + bright;
				red[y][x] = Math.max(0, red[y][x]);
				blue[y][x] = Math.max(0, blue[y][x]);
				green[y][x] = Math.max(0, green[y][x]);
				red[y][x] = Math.min(255, red[y][x]);
				blue[y][x] = Math.min(255, blue[y][x]);
				green[y][x] = Math.min(255, green[y][x]);
				Color d = new Color(red[y][x], green[y][x], blue[y][x]);
				tempImage.setRGB(x, y, d.getRGB());
			}
		}

		RescaleOp rescale = new RescaleOp(1, 0, null);
		return rescale.filter(tempImage, null);
	}
}
