package com.taotao.common.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.MalformedURLException;

/**
 * ftp上传下载工具类
 * <p>Title: FtpUtil</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年7月29日下午8:11:51
 * @version 1.0
 */
public class FtpUtil {

	/**
	 * 初始化ftp服务器
	 */
	public static FTPClient createFtpClient(String hostname, Integer port, String username, String password) {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
		try {
			System.out.println("connecting...ftp服务器:"+hostname+":"+port);
			ftpClient.connect(hostname, port); //连接ftp服务器
			ftpClient.login(username, password); //登录ftp服务器
			int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
			if(!FTPReply.isPositiveCompletion(replyCode)){
				return null;
			}else
				return ftpClient;
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传文件
	 * @param pathname ftp服务保存地址
	 * @param fileName 上传到ftp的文件名
	 *  @param inputStream 待上传文件的名称（绝对地址） *
	 * @return
	 */
	public static boolean uploadFile(FTPClient ftpClient, String pathname, String fileName, InputStream inputStream){
		boolean flag = false;
		try{
			ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
			CreateDirecroty(ftpClient, pathname);
			ftpClient.makeDirectory(pathname);
			ftpClient.changeWorkingDirectory(pathname);
			ftpClient.storeFile(fileName, inputStream);
			inputStream.close();
			ftpClient.logout();
			flag = true;
			System.out.println("上传文件成功");
		}catch (Exception e) {
			System.out.println("上传文件失败");
			e.printStackTrace();
		}finally{
			if(ftpClient.isConnected()){
				try{
					ftpClient.disconnect();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(null != inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/** * 下载文件 *
	 * @param pathname FTP服务器文件目录 *
	 * @param filename 文件名称 *
	 * @param localpath 下载后的文件路径 *
	 * @return */
	public static  boolean downloadFile(FTPClient ftpClient, String pathname, String filename, String localpath){
		boolean flag = false;
		OutputStream os=null;
		try {
			System.out.println("开始下载文件");
			//切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for(FTPFile file : ftpFiles){
				if(filename.equalsIgnoreCase(file.getName())){
					File localFile = new File(localpath + "/" + file.getName());
					os = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), os);
					os.close();
				}
			}
			ftpClient.logout();
			flag = true;
			System.out.println("下载文件成功");
		} catch (Exception e) {
			System.out.println("下载文件失败");
			e.printStackTrace();
		} finally{
			if(ftpClient.isConnected()){
				try{
					ftpClient.disconnect();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(null != os){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/** * 删除文件 *
	 * @param pathname FTP服务器保存目录 *
	 * @param filename 要删除的文件名称 *
	 * @return */
	public static boolean deleteFile(FTPClient ftpClient, String pathname, String filename){
		boolean flag = false;
		try {
			System.out.println("开始删除文件");
			//切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
			ftpClient.dele(filename);
			ftpClient.logout();
			flag = true;
			System.out.println("删除文件成功");
		} catch (Exception e) {
			System.out.println("删除文件失败");
			e.printStackTrace();
		} finally {
			if(ftpClient.isConnected()){
				try{
					ftpClient.disconnect();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return flag;
	}


	//创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
	private static boolean CreateDirecroty(FTPClient ftpClient, String remote) throws IOException {
		boolean success = true;
		String directory = remote + "/";
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(ftpClient, new String(directory))) {
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			String path = "";
			String paths = "";
			while (true) {
				String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
				path = path + "/" + subDirectory;
				if (!existFile(ftpClient, path)) {
					if (makeDirectory(ftpClient, subDirectory)) {
						changeWorkingDirectory(ftpClient, subDirectory);
					} else {
						System.out.println("创建目录[" + subDirectory + "]失败");
						changeWorkingDirectory(ftpClient, subDirectory);
					}
				} else {
					changeWorkingDirectory(ftpClient, subDirectory);
				}

				paths = paths + "/" + subDirectory;
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return success;
	}

	//改变目录路径
	private static boolean changeWorkingDirectory(FTPClient ftpClient , String directory) {
		boolean flag = true;
		try {
			flag = ftpClient.changeWorkingDirectory(directory);
			if (flag) {
				System.out.println("进入文件夹" + directory + " 成功！");

			} else {
				System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return flag;
	}

	//判断ftp服务器文件是否存在
	private static boolean existFile(FTPClient ftpClient, String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}

	//创建目录
	private static boolean makeDirectory(FTPClient ftpClient, String dir) {
		boolean flag = true;
		try {
			flag = ftpClient.makeDirectory(dir);
			if (flag) {
				System.out.println("创建文件夹" + dir + " 成功！");

			} else {
				System.out.println("创建文件夹" + dir + " 失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}