package day06.varify;

import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class VerifyCode {
	private int width = 70;
	private int height = 35;
	private Color bgColor = new Color(240, 240, 240);
	private String codes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	private String[] fontNames = {"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"};
	private int[] fontStyles = {0, 1, 2, 3};
	private int[] fontSizes = {24, 25, 26, 27, 28};
	
	private String code;//最后的字符串
	
	private Random r = new Random();
	
	// 获取验证码上的文本内容
	// 注意，这个方法必须在getImage()方法之后调用
	public String getCode() {
		return code;
	}

	// 保存图片到指定的流中
	public static void drawImage(BufferedImage img, OutputStream output) throws IOException {
		ImageIO.write(img, "JPEG", output);
	}
	
	public BufferedImage getImage() {
		/*
		 * 1使用BufferedImage，创建一张内存中的图片
		 * 2设置背景色
		 * 3循环4圈
		 *   4生成一个随机字符（保存到一个StringBuilder）
		 *   5生成随机颜色
		 *   6生成随机的字体
		 *   7设置字符x轴坐标
		 *   8画
		 * 把StringBuilder中的东西保存到一个字符串中，并且为这个字符串设置一个getXXX()方法
		 * 9添加干扰线
		 * 10return
		 */
		BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) img.getGraphics();
		setBgColor(g2);
		StringBuilder sb = new StringBuilder(4);
		for(int i = 0; i < 4; i++) {
			String ch = randomChar();
			sb.append(ch);
			g2.setColor(randomColor());
			g2.setFont(randomFont());
			float x = this.width * 1.0F / 4 * i;
			g2.drawString(ch, x, this.height-5);
		}
		this.code = sb.toString();
		drawLine(g2, 5);
		return img;
	}

	// 画干扰线
	private void drawLine(Graphics2D g2, int cnt) {
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1.5F));
		for(int i = 0; i < cnt; i++) {
			int x1 = r.nextInt(this.width + 1);
			int x2 = r.nextInt(this.width + 1);
			int y1 = r.nextInt(this.height + 1);
			int y2 = r.nextInt(this.height + 1);
			g2.drawLine(x1, y1, x2, y2);
		}
	}
	
	// 随机字体
	private Font randomFont() {
		int index = r.nextInt(fontNames.length);
		String fontName = fontNames[index];
		
		index = r.nextInt(fontStyles.length);
		int fontStyle = fontStyles[index];
		
		index = r.nextInt(fontSizes.length);
		int fontSize = fontSizes[index];
		
		return new Font(fontName, fontStyle, fontSize);
	}

	// 生成随机颜色
	private Color randomColor() {
		int red = r.nextInt(256);
		int green = r.nextInt(256); 
		int blue = r.nextInt(256);
		return new Color(red, green, blue);
	}

	// 生成随机字符
	private String randomChar() {
		int index = r.nextInt(codes.length());
		return codes.charAt(index) + "";
	}

	// 设置背景色
	private void setBgColor(Graphics2D g2) {
		g2.setColor(this.bgColor);
		g2.fillRect(0, 0, width, height);
	}
	@Test
	public void fun1(){
		BufferedImage image = this.getImage();
		try {
			drawImage(image,new FileOutputStream("F:/学习/JAVA/do/picture.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
