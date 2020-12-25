package com.steve;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author stevexu
 * @since 2020/11/14
 */
public class UnitPriceTest {

	public static void main(String args[]) {
        List<Product> products = new ArrayList<>();
		products.add(new Product(76005355,13020,"도담이 엠보싱 유아물티슈 캡형, 10팩"));
		products.add(new Product(226897186,15300,"네츄럴오가닉 퓨어 플레인 캡형100매10팩"));
		products.add(new Product(2558995253L,14120,"미엘 퓨어 민트 물티슈 캡형, 100매, 20팩"));
		String pieceKeyWords = "매";
		String packKeywords = "팩,개";
		for (Product product:products) {
			extractUnitPrice(product,pieceKeyWords,packKeywords);
		}
	}

	public static Product extractUnitPrice(Product product,
		String pieceKeyWords,String packKeywords ) {
 	    int piece = 0;
 	    int pack = 0;
        for (String pieceKeyWord : pieceKeyWords.split(",")) {
			Pattern pattern = Pattern.compile("\\d+?" + pieceKeyWord);
			Matcher matcher = pattern.matcher(product.getTitle());
			while (matcher.find()) {
				String res = matcher.group();
				res = res.substring(0, res.indexOf(pieceKeyWord));
				piece = Integer.parseInt(res);
				break;
			}
			if (piece > 0) {
				product.setUnitPiece(piece);
				break;
			}
		}
		for (String packKeyword : packKeywords.split(",")) {
			Pattern pattern = Pattern.compile("\\d+?" + packKeyword);
			Matcher matcher = pattern.matcher(product.getTitle());
			while (matcher.find()) {
				String res = matcher.group();
				res = res.substring(0, res.indexOf(packKeyword));
				pack = Integer.parseInt(res);
				break;
			}
			if (pack > 0) {
				product.setUnitPack(pack);
				break;
			}
		}
		if (piece > 0 && pack > 0) {
			float unitPrice = ((float)product.getPrice()) / (piece * pack);
			product.setUnitPrice(unitPrice);
		}
		System.out.println(product);
		return product;
	}

}



class Product {
	long itemId;
	int price;
	String title;
	int unitPiece;
	int unitPack;
	float unitPrice;

	public Product(long itemId, int price, String title) {
		this.itemId = itemId;
		this.price = price;
		this.title = title;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUnitPiece() {
		return unitPiece;
	}

	public void setUnitPiece(int unitPiece) {
		this.unitPiece = unitPiece;
	}

	public int getUnitPack() {
		return unitPack;
	}

	public void setUnitPack(int unitPack) {
		this.unitPack = unitPack;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "Product{" +
			"itemId=" + itemId +
			", price=" + price +
			", title='" + title + '\'' +
			", unitPiece=" + unitPiece +
			", unitPack=" + unitPack +
			", unitPrice=" + unitPrice +
			'}';
	}
}
