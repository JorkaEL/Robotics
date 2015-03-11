package com.robotics.robotics;

/**
 * Created by xavier on 11/03/15.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Handler;


public class BtInterface {

        private BluetoothDevice device = null;// le périphérique (le module bluetooth)
        private BluetoothSocket socket = null;
        private InputStream receiveStream = null;// Canal de réception
        private OutputStream sendStream = null;// Canal d'émission
        //private ReceiverThread receiverThread;

    public BtInterface() {

        // On récupère la liste des périphériques associés
        Set<BluetoothDevice> setpairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        BluetoothDevice[] pairedDevices = (BluetoothDevice[]) setpairedDevices.toArray(new BluetoothDevice[setpairedDevices.size()]);

        // On parcours la liste pour trouver notre module bluetooth
        for(int i=0;i<pairedDevices.length;i++)
        {
            // On teste si ce périphérique contient le nom du module bluetooth connecté au microcontrôleur
            if(pairedDevices[i].getName().contains("ModuleBluetooth")) {

                device = pairedDevices[i];
                try {
                    // On récupère le socket de notre périphérique
                    socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

                    receiveStream = socket.getInputStream();// Canal de réception (valide uniquement après la connexion)
                    sendStream = socket.getOutputStream();// Canal d'émission (valide uniquement après la connexion)

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        //receiverThread = new ReceiverThread(h); // On créer le thread de réception des données avec l'Handler venant du thread UI
    }

    public void connect() {
        new Thread() {
            @Override public void run() {
                try {
                    socket.connect();// Tentative de connexion
                    // Connexion réussie
                    //receiverThread.start();
                } catch (IOException e) {
                    // Echec de la connexion
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void sendData(String data) {
        try {
            // On écrit les données dans le buffer d'envoi
            sendStream.write(data.getBytes());

            // On s'assure qu'elles soient bien envoyés
            sendStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private class ReceiverThread extends Thread {
        Handler handler;

        ReceiverThread(Handler h) {
            handler = h;
        }

        @Override public void run() {
            while(true) {
                try {
                    // On teste si des données sont disponibles
                    if(receiveStream.available() > 0)
                    {
                        byte buffer[] = new byte[100];

                        // On lit les données, k représente le nombre de bytes lu
                        int k = receiveStream.read(buffer, 0, 100);

                        if(k > 0) {
                            // On convertit les données en String
                            byte rawdata[] = new byte[k];
                            for(int i=0;i<k;i++)
                                rawdata[i] = buffer[i];

                            String data = new String(rawdata);

                            // On envoie les données dans le thread de l'UI pour les affichées
                            Message msg = handler.obtainMessage();
                            Bundle b = new Bundle();
                            b.putString("receivedData", data);
                            msg.setData(b);
                            handler.sendMessage(msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
