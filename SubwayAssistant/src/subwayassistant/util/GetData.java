package subwayassistant.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import subwayassistant.ui.FrmMain;
import subwayassistant.model.Line;

public class GetData {//��ȡ.txt�ļ��е���Ϣ
	public static int linenum = 0;
	@SuppressWarnings("null")
	public GetData(String pathname, List<Line> lines) throws IOException{
		//���ļ�׼��
		File file = new File(pathname);
		InputStreamReader rdr = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(rdr);
		try {
			String content=null;
			while((content=br.readLine())!=null) {
				linenum++;
				Line newline = new Line();
				List<String> line = new ArrayList<String>();
				String[] station_single = content.split(" ");
				String linename = station_single[0];//��һ��Ԫ��Ϊ��·��
				for(int i=1;i<station_single.length;i++) {//�洢����վ��
					if(i==station_single.length-1 && station_single[i].equals(station_single[1]))//������
						continue;
					line.add(station_single[i]);
				}
				newline.setName(linename);
				newline.setStations(line);
				lines.add(newline);
			}
			br.close();
		} catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br == null)
                try {
                	br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
	}
}