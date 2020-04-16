package jp.kaiz.atsassistmod.controller;

public class TCThreadManager {
	private static int cnt;

	public synchronized void addSync() {
		// 同期オブジェクトの追加
		cnt++;
	}

	public synchronized void delSync() {
		// 同期オブジェクトの削除
		cnt--;
		try {
			notify();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public synchronized void waitSync() {
		// 同期オブジェクトの待ち合わせ（子がすべて終了するまで待つ）
		while (cnt > 0) {
			try {
				wait();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
