package com.yun.jt808.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

/**
 * 位运算
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:34:14
 */
public class BitOperator {
	
	/**
	 * byte数组分割成小段数组
	 * @param data 源byte数组
	 * @param startIndex 开始未知
	 * @param len 长度
	 * @return
	 */
	public byte[] bytesToSplitBytes(byte[] data,int startIndex,int len){
		byte[] temp = new byte[len];
		System.arraycopy(data, startIndex, temp, 0, len - 1);
		return temp;
	}

	/**
	 * 将字节数组转换成二进制数组
	 * @param data 源数组
	 * @param startIndex 开始位置
	 * @param len 长度
	 * @return bit[]
	 */
	public byte[] bytesToBitArray(byte[] data , int startIndex , int len){
		byte[] bytes = new byte[len];
		System.arraycopy(data, startIndex, bytes, 0, len);
		return bytesToBitArray(bytes);
	}
	
	/**
	 * byte[] to byte[bit] 将字节数组转换成二进制数组
	 * @param bytes
	 * @return
	 */
	public byte[] bytesToBitArray(byte[] bytes){
		if(bytes == null || bytes.length == 0)
		{
			throw new NullPointerException("空字节数组");
		}
		int size = 8 * bytes.length;
		
		byte[] bits = new byte[size];
		//数组从小到大处理
		for(int i = 0; i < bytes.length; i++){
			//遍历字节数组
			byte b = bytes[i];
			int tempIndex = (8 * (i + 1)) - 1;
			for(int j = 7; j >= 0; j--){
				bits[tempIndex] = (byte) (b & 1);
				b = (byte) (b >> 1);
				tempIndex--;
			}
		}
		return bits;
	}
	
	/**
	 * 把一个整形该为byte
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte integerTo1Byte(int value) {
		return (byte) (value & 0xFF);
	}

	/**
	 * 把一个整形该为1位的byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] integerTo1Bytes(int value) {
		byte[] result = new byte[1];
		result[0] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * 把一个整形改为2位的byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] integerTo2Bytes(int value) {
		byte[] result = new byte[2];
		result[0] = (byte) ((value >>> 8) & 0xFF);
		result[1] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * 把一个整形改为3位的byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] integerTo3Bytes(int value) {
		byte[] result = new byte[3];
		result[0] = (byte) ((value >>> 16) & 0xFF);
		result[1] = (byte) ((value >>> 8) & 0xFF);
		result[2] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * 把一个整形改为4位的byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] integerTo4Bytes(int value){
		byte[] result = new byte[4];
		result[0] = (byte) ((value >>> 24) & 0xFF);
		result[1] = (byte) ((value >>> 16) & 0xFF);
		result[2] = (byte) ((value >>> 8) & 0xFF);
		result[3] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * 把byte[]转化位整形,通常为指令用
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int byteToInteger(byte[] value) {
		int result;
		if (value.length == 1) {
			result = oneByteToInteger(value[0]);
		} else if (value.length == 2) {
			result = twoBytesToInteger(value);
		} else if (value.length == 3) {
			result = threeBytesToInteger(value);
		} else if (value.length == 4) {
			result = fourBytesToInteger(value);
		} else {
			result = fourBytesToInteger(value);
		}
		return result;
	}

	/**
	 * 把一个byte转化位整形,通常为指令用 0xFF=255
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int oneByteToInteger(byte value) {
		return (int) value & 0xFF;
	}
	
	public static byte[] getBooleanArray(int b){
		byte[] array = new byte[Integer.bitCount(b)];
		for(int i = (array.length -1); i >= 0; i--){
			array[i] = (byte)(b & 1);
			b = (byte) (b >> 1);
		}
		return array;
	}
	
	public static int[] intToIntegerList(int ind){
		if(ind < 0){
			
		}
		int[] array = new int[Integer.bitCount(ind)];
		int count = 0;
		for(int i = 1; i > 0 && count < array.length; i <<= 1){
			if((ind & i) != 0){
				array[count++] = i;
			}
		}
		return array;
	}

	/**
	 * 把一个2位的数组转化位整形
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int twoBytesToInteger(byte[] value) {
		// if (value.length < 2) {
		// throw new Exception("Byte array too short!");
		// }
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		return ((temp0 << 8) + temp1);
	}

	/**
	 * 把一个3位的数组转化位整形
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int threeBytesToInteger(byte[] value) {
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		return ((temp0 << 16) + (temp1 << 8) + temp2);
	}

	/**
	 * 把一个4位的数组转化位整形,通常为指令用
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int fourBytesToInteger(byte[] value) {
		// if (value.length < 4) {
		// throw new Exception("Byte array too short!");
		// }
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		int temp3 = value[3] & 0xFF;
		return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
	}

	/**
	 * 把一个4位的数组转化位整形
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long fourBytesToLong(byte[] value) throws Exception {
		// if (value.length < 4) {
		// throw new Exception("Byte array too short!");
		// }
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		int temp3 = value[3] & 0xFF;
		return (((long) temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
	}

	/**
	 * 把一个数组转化长整形
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long bytes2Long(byte[] value) {
		long result = 0;
		int len = value.length;
		int temp;
		for (int i = 0; i < len; i++) {
			temp = (len - 1 - i) * 8;
			if (temp == 0) {
				result += (value[i] & 0x0ff);
			} else {
				result += (value[i] & 0x0ff) << temp;
			}
		}
		return result;
	}

	/**
	 * 把一个长整形改为byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] longToBytes(long value){
		return longToBytes(value, 8);
	}

	/**
	 * 把一个长整形改为byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] longToBytes(long value, int len) {
		byte[] result = new byte[len];
		int temp;
		for (int i = 0; i < len; i++) {
			temp = (len - 1 - i) * 8;
			if (temp == 0) {
				result[i] += (value & 0x0ff);
			} else {
				result[i] += (value >>> temp) & 0x0ff;
			}
		}
		return result;
	}

	/**
	 * 得到一个消息ID
	 * 
	 * @return
	 * @throws Exception
	 */
	public byte[] generateTransactionID() throws Exception {
		byte[] id = new byte[16];
		Random random = new Random();
		System.arraycopy(integerTo2Bytes((int) (random.nextInt(65536))), 0, id, 0, 2);
		System.arraycopy(integerTo2Bytes((int) (random.nextInt(65536))), 0, id, 2, 2);
		System.arraycopy(integerTo2Bytes((int) (random.nextInt(65536))), 0, id, 4, 2);
		System.arraycopy(integerTo2Bytes((int) (random.nextInt(65536))), 0, id, 6, 2);
		System.arraycopy(integerTo2Bytes((int) (random.nextInt(65536))), 0, id, 8, 2);
		System.arraycopy(integerTo2Bytes((int) (random.nextInt(65536))), 0, id, 10, 2);
		System.arraycopy(integerTo2Bytes((int) (random.nextInt(65536))), 0, id, 12, 2);
		System.arraycopy(integerTo2Bytes((int) (random.nextInt(65536))), 0, id, 14, 2);
		return id;
	}

	/**
	 * 把IP拆分位int数组
	 * 
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public int[] getIntIPValue(String ip) throws Exception {
		String[] sip = ip.split("[.]");
		// if (sip.length != 4) {
		// throw new Exception("error IPAddress");
		// }
		int[] intIP = { Integer.parseInt(sip[0]), Integer.parseInt(sip[1]), Integer.parseInt(sip[2]),
				Integer.parseInt(sip[3]) };
		return intIP;
	}

	/**
	 * 把byte类型IP地址转化位字符串
	 * 
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public String getStringIPValue(byte[] address) throws Exception {
		int first = this.oneByteToInteger(address[0]);
		int second = this.oneByteToInteger(address[1]);
		int third = this.oneByteToInteger(address[2]);
		int fourth = this.oneByteToInteger(address[3]);

		return first + "." + second + "." + third + "." + fourth;
	}

	/**
	 * 合并字节数组
	 * 
	 * @param first
	 * @param rest
	 * @return
	 */
	public byte[] concatAll(byte[] first, byte[]... rest) {
		int totalLength = first.length;
		for (byte[] array : rest) {
			if (array != null) {
				totalLength += array.length;
			}
		}
		byte[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (byte[] array : rest) {
			if (array != null) {
				System.arraycopy(array, 0, result, offset, array.length);
				offset += array.length;
			}
		}
		return result;
	}

	/**
	 * 合并字节数组
	 * 
	 * @param rest
	 * @return
	 */
	public byte[] concatAll(List<byte[]> rest) {
		int totalLength = 0;
		for (byte[] array : rest) {
			if (array != null) {
				totalLength += array.length;
			}
		}
		byte[] result = new byte[totalLength];
		int offset = 0;
		for (byte[] array : rest) {
			if (array != null) {
				System.arraycopy(array, 0, result, offset, array.length);
				offset += array.length;
			}
		}
		return result;
	}

	public float byte2Float(byte[] bs) {
		return Float.intBitsToFloat(
				(((bs[3] & 0xFF) << 24) + ((bs[2] & 0xFF) << 16) + ((bs[1] & 0xFF) << 8) + (bs[0] & 0xFF)));
	}

	public float byteBE2Float(byte[] bytes) {
		int l;
		l = bytes[0];
		l &= 0xff;
		l |= ((long) bytes[1] << 8);
		l &= 0xffff;
		l |= ((long) bytes[2] << 16);
		l &= 0xffffff;
		l |= ((long) bytes[3] << 24);
		return Float.intBitsToFloat(l);
	}

	public int getCheckSum4JT808(byte[] bs, int start, int end) {
		if (start < 0 || end > bs.length){
			throw new ArrayIndexOutOfBoundsException("getCheckSum4JT808 error : index out of bounds(start=" + start
					+ ",end=" + end + ",bytes length=" + bs.length + ")");
		}
		int cs = 0;
		for (int i = start; i <= end; i++) {
			cs ^= bs[i];
		}
		return cs;
	}
	
	public int getBitRange(int number, int start, int end) {
		if (start < 0){
			throw new IndexOutOfBoundsException("min index is 0,but start = " + start);
		}
		if (end >= Integer.SIZE){
			throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but end = " + end);
		}

		return (number << Integer.SIZE - (end + 1)) >>> Integer.SIZE - (end - start + 1);
	}

	public int getBitAt(int number, int index) {
		if (index < 0){
			throw new IndexOutOfBoundsException("min index is 0,but " + index);
		}
		if (index >= Integer.SIZE){
			throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but " + index);
		}

		return ((1 << index) & number) >> index;
	}

	public int getBitAtS(int number, int index) {
		String s = Integer.toBinaryString(number);
		return Integer.parseInt(s.charAt(index) + "");
	}

	@Deprecated
	public int getBitRangeS(int number, int start, int end) {

		String s = Integer.toBinaryString(number);
		StringBuilder sb = new StringBuilder(s);
		while (sb.length() < Integer.SIZE) {
			sb.insert(0, "0");
		}
		String tmp = sb.reverse().substring(start, end + 1);
		sb = new StringBuilder(tmp);
		return Integer.parseInt(sb.reverse().toString(), 2);
	}

	public List<Integer> getAdByteByFxtoFx(byte[] bytes, int f1, int f2) {
		List<Integer> list = new ArrayList<Integer>();
		//获取f1的实际位置
		for(int i=0; i < bytes.length; i++){
			if(bytes[i] == (byte)f1 && (i + 21) < bytes.length && bytes[i + 21] == (byte)f2){
				byte[] adbyte = new byte[20];
				System.arraycopy(bytes, i+1, adbyte, 0, adbyte.length);
				List<Byte> temp = new ArrayList<Byte>();
				for(Byte b : adbyte){
					temp.add(b);
					if(temp.size() == 2){
						list.add(byteToInteger(new byte[]{temp.get(0), temp.get(1)}));
						temp.clear();
					}
				}
			}
		}
		
//		List<Byte> bytelist = null;
//		for(int i = 0; i < bytes.length; i++){
//			byte value = bytes[i];
//			if(value == (byte)f1){
//				bytelist = new ArrayList<Byte>();
//			}
//			if(value == (byte)f2){
//				break;
//			}
//			if(value != (byte)f1 && bytelist != null && value != 0x01){
//				bytelist.add(value);
//				if(bytelist.size() == 2){
//					list.add(byteToInteger(new byte[]{bytelist.get(0), bytelist.get(1)}));
//					bytelist.clear();
//				}
//			}
//		}
		//转为
		return list;
	}

	public List<Integer> getBytesToSplitPointsToCovertList(byte[] bytes, int f4) {
		List<Integer> list = new ArrayList<Integer>();
			
		List<Byte> bytelist = null;
		for(int i = 0; i < bytes.length; i++){
			byte value = bytes[i];
			if(value == (byte)f4){
				bytelist = new ArrayList<Byte>();
			}
			if(value != (byte)f4 && bytelist != null && value != 0x01){
				bytelist.add(value);
				if(bytelist.size() == 2){
					list.add(byteToInteger(new byte[]{bytelist.get(0), bytelist.get(1)}));
					bytelist.clear();
				}
			}
		}
		//转为
		return list;
	}
}
