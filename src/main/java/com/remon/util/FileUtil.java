package com.remon.util;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FileUtil {

	// Properties
	public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static String WEB_DRIVER_PATH = "E:/chromedriver.exe";

	public static String FILE_PATH = "F:\\test";
	public static void main(String[] args) {
		String url = "https://www.avdbs.com/menu/search.php?kwd=";
		
		File dir = new File(FILE_PATH);
		File[] filesArr = dir.listFiles();
		String oriFile = "";
		String cutFile = "";
		String ext = "";
		StringBuffer newFile = new StringBuffer();
		String tileChange= "";
		// 드라이버 설정
		try {
			System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 크롬 설정을 담은 객체 생성
		ChromeOptions options = new ChromeOptions();
		// 브라우저가 눈에 보이지 않고 내부적으로 돈다.
		// 설정하지 않을 시 실제 크롬 창이 생성되고, 어떤 순서로 진행되는지 확인할 수 있다.
		options.addArguments("headless");

		// 위에서 설정한 옵션은 담은 드라이버 객체 생성
		// 옵션을 설정하지 않았을 때에는 생략 가능하다.
		// WebDriver객체가 곧 하나의 브라우저 창이라 생각한다.
		WebDriver driver = new ChromeDriver(options);
		System.out.println("파일목록::"+filesArr.length);
		for(int i = 0; i < filesArr.length; i++) {
			newFile.setLength(0);
			oriFile = filesArr[i].getName();
			File file = new File(FILE_PATH+File.separator+oriFile);
			// 1. 파일 이름 조회
			cutFile = oriFile.substring(0, oriFile.lastIndexOf(".") );
			ext = oriFile.substring(oriFile.lastIndexOf("."));
			newFile.append(cutFile);
			
			// 이동을 원하는 url
			String url1 = url+cutFile;
			try {
				Thread.sleep(70000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				// WebDriver을 해당 url로 이동한다.
				driver.get(url1);

				// 브라우저 이동시 생기는 로드시간을 기다린다.
				// HTTP응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
				try {
					Thread.sleep(70000);
				} catch (InterruptedException e) {
				}

				// class="nav" 인 모든 태그를 가진 WebElement리스트를 받아온다.
				// WebElement는 html의 태그를 가지는 클래스이다.
				
				List<WebElement> el1 = driver.findElements(By.className("lnk_dvd_dtl"));

				if(el1 != null && el1.size() > 0) {
					driver.get(el1.get(0).getAttribute("href"));
					
					try {
						Thread.sleep(70000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					List<WebElement> el3 = driver.findElements(By.className("cast"));
					WebElement tileId = driver.findElement(By.id("title_jp"));
					if(el3 != null) {
						for(int j = 0; j < el3.size(); j++) {
							if(j == 0) {
								newFile.append(" "+el3.get(j).getText().replace("#", ""));
							} else {
								newFile.append(" "+el3.get(j).getText());
							}
						}
					}
					tileChange = tileId.getText().replaceAll("\\*", "");
					tileChange = tileChange.replaceAll("\"", "");
					newFile.append(" "+tileChange);
					newFile.append(ext);
					
					System.out.println( FILE_PATH+File.separator+newFile.toString() );
					file.renameTo(new File(FILE_PATH+File.separator+newFile.toString()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			if (driver != null) {
				driver.close(); // 드라이버 연결 해제
				driver.quit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}

		System.out.println("종료");
		
	}

}
