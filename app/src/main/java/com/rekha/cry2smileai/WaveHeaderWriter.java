package com.rekha.cry2smileai;


import android.media.AudioFormat;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileInputStream;
import java.io.IOException;

public class WaveHeaderWriter {
    private String filePath;
    private WaveConfig waveConfig;

    public WaveHeaderWriter(String filePath) {
        this.filePath = filePath;
        waveConfig=new WaveConfig();
    }

    public void writeHeader() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(filePath));
        long totalAudioLen = inputStream.getChannel().size() - 44;
        long totalDataLen = totalAudioLen + 36;
        int channels = (waveConfig.getChannels() == AudioFormat.CHANNEL_IN_MONO) ? 1 : 2;

        long sampleRate = waveConfig.getSampleRate();
        long byteRate = (bitPerSample(waveConfig.getAudioEncoding()) * waveConfig.getSampleRate() * channels / 8);

        byte[] header = getWavFileHeaderByteArray(
                totalAudioLen, totalDataLen, sampleRate, channels, byteRate, bitPerSample(waveConfig.getAudioEncoding()));

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(filePath), "rw");
        randomAccessFile.seek(0);
        randomAccessFile.write(header);
        randomAccessFile.close();
    }

    private byte[] getWavFileHeaderByteArray(long totalAudioLen, long totalDataLen, long longSampleRate,
                                             int channels, long byteRate, int bitsPerSample) {
        byte[] header = new byte[44];
        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (channels * (bitsPerSample / 8));
        header[33] = 0;
        header[34] = (byte) bitsPerSample;
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        return header;
    }

    private int bitPerSample(int audioEncoding) {
        switch (audioEncoding) {
            case AudioFormat.ENCODING_PCM_8BIT:
                return 8;
            case AudioFormat.ENCODING_PCM_16BIT:
                return 16;
            case AudioFormat.ENCODING_PCM_32BIT:
                return 32;
            default:
                return 16;
        }
    }
}



