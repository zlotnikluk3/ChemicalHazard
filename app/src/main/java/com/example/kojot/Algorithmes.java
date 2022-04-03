package com.example.kojot;

import org.shredzone.commons.suncalc.SunTimes;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Algorithmes {

    final double[][] ktab = new double[4][9];

    public Algorithmes() {
        iniTab();
    }

    public boolean dlaw(double p, double pa, double cp, double cv) {
        double y = cp / cv;
        if (p / pa >= (Math.pow((y + 1) / 2, y / (y - 1))))
            return true;
        else
            return false;
    }

    public double wypDlaw(double ah, double cp, double cv, double T, double Ro, double p, double M) {
        double Q = 0;
        double y = cp / cv;
        if (Ro == 0) {
            Q = ah * p * Math.sqrt(((y * M) / (8314 * T)) * Math.pow(2 / (y + 1), (y + 1) / (y - 1)));
        } else if (T == 0) {
            Q = ah * Math.sqrt(p * Ro * y * Math.pow(2 / (y + 1), (y + 1) / (y - 1)));
        }
        return Q;
    }

    public double wypNdlaw(double ah, double cp, double cv, double T, double Ro, double p, double pa, double M) {
        double Q = 0.0;
        double y = cp / cv;
        if (Ro == 0) {
            Q = ah * p * Math.sqrt(2 * (M / (8314 * T)) * (y / (y - 1)) * (Math.pow(pa / p, 2 / y) - Math.pow(pa / p, (y + 1) / y)));
        } else if (T == 0) {
            Q = ah * Math.sqrt(2 * Ro * p * (y / (y - 1)) * (Math.pow(pa / p, 2 / y) - Math.pow(pa / p, (y + 1) / y)));
        }
        return Q;
    }

    public double intensProm(Date d, int h, int min, double lat, double lng, int okt) {
        double Ip;

        Ip = 10 * kc(okt) * ((26 * (1 - Math.cos(0.455 + (0.01578 * dzien(d))))) + 58.15
                - (13.26 * Math.sqrt(5.18 + Math.pow(getH(h, min) - getHkulm(d, lat, lng), 2))));

        return Ip;
    }

    public double wyznL(Date d, int h, int min, double Ip, double u, double lat, double lng, int okt) {
        double l;
        int x = (int) Math.floor(u);
        if (x > 8) {
            x = 8;
        }
        if (getH(h, min) > getHrise(d, lat, lng) && getH(h, min) < getHset(d, lat, lng)) {
            l = 3.6 - (ktab[2][x] * Math.log10(0.01 * Ip + 1)) - (ktab[3][x] * Ip * 0.1);
        } else {
            l = 3.6 - (ktab[0][x] * Math.log(0.01 * okt + 0.2)) - (ktab[1][x] * (8 - okt));
        }

        return l;
    }

    public double GausX(double q, double u, boolean mw, String cl, double C) {
        return Math.sqrt((1000000 * q) / (Math.PI * 2 * C * getY(mw, cl) * getZ(mw, cl)));
    }

    public double boilLiqQc(double ah, double Ro, double p, double pa, double hc) {
        double Q = 0;
        Q = 0.61 * ah * Ro * Math.sqrt((2 * ((p - pa) / Ro)) + (19.62 * hc));
        return Q;
    }

    public double boilLiqQq(double Cp, double To, double Twrz, double Qpar, double Qc) {
        return Qc * (Cp / 0.071) * (To - Twrz) / (Qpar * 1000);
    }

    public double kG(double Nsc, double u, double dp) {
        return 0.00482 * Math.pow(Nsc, -0.67) * Math.pow(u, 0.78) * Math.pow(dp, -0.11);
    }

    public double qPrePar(double kg, double dp, double ps, double M, double T) {
        return (kg * dp * dp * 3.14 * ps * M) / (4 * 8314 * T);
    }

    public int dzien(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d); // Give your own date
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    public double getH(int h, int min) {
        return h + (min / 60.0);
    }

    public double getHkulm(Date d, double lat, double lng) {
        double res = 0;
        SunTimes times = SunTimes.compute().on(d) // set a date
                .at(lat, lng) // set a location
                .execute(); // get the results
        res = times.getNoon().getHours() + (times.getNoon().getMinutes() / 60.0);
        return res;
    }

    public double getHrise(Date d, double lat, double lng) {
        double res = 0;
        SunTimes times = SunTimes.compute().on(d) // set a date
                .at(lat, lng) // set a location
                .execute(); // get the results
        res = times.getRise().getHours() + (times.getNoon().getMinutes() / 60.0);
        return res;
    }

    public double getHset(Date d, double lat, double lng) {
        double res = 0;
        SunTimes times = SunTimes.compute().on(d) // set a date
                .at(lat, lng) // set a location
                .execute(); // get the results
        res = times.getSet().getHours() + (times.getNoon().getMinutes() / 60.0);
        return res;
    }

    public double kc(int okt) {
        switch (okt) {
            case 0:
                return 1.1;
            case 1:
                return 0.9;
            case 2:
                return 0.8;
            case 3:
                return 0.76;
            case 4:
                return 0.705;
            case 5:
                return 0.655;
            case 6:
                return 0.57;
            case 7:
                return 0.455;
            default:
                return 0.2;
        }
    }

    public void iniTab() {
        ktab[0][0] = 32.25;
        ktab[0][1] = 23.50;
        ktab[0][2] = 17.20;
        ktab[0][3] = 5.30;
        ktab[0][4] = 2.52;
        ktab[0][5] = 0.319;
        ktab[0][6] = 0.161;
        ktab[0][7] = 0.0;
        ktab[0][8] = 0.0;

        ktab[1][0] = 1.31;
        ktab[1][1] = 0.882;
        ktab[1][2] = 0.588;
        ktab[1][3] = 0.0355;
        ktab[1][4] = -0.0609;
        ktab[1][5] = -0.0845;
        ktab[1][6] = -0.0249;
        ktab[1][7] = -0.0225;
        ktab[1][8] = -0.00625;

        ktab[2][0] = 3.275;
        ktab[2][1] = 2.92;
        ktab[2][2] = 2.73;
        ktab[2][3] = 2.265;
        ktab[2][4] = 2.06;
        ktab[2][5] = 1.155;
        ktab[2][6] = 0.274;
        ktab[2][7] = 0.153;
        ktab[2][8] = 0.0;

        ktab[3][0] = 0.00462;
        ktab[3][1] = 0.00585;
        ktab[3][2] = 0.00606;
        ktab[3][3] = 0.00710;
        ktab[3][4] = 0.00442;
        ktab[3][5] = 0.0095;
        ktab[3][6] = 0.0129;
        ktab[3][7] = 0.0107;
        ktab[3][8] = 0.00792;
    }

    public String LtoClass(double l) {
        if (l >= 0 && l < 1)
            return "A";
        else if (l >= 1 && l < 2)
            return "B";
        else if (l >= 2 && l < 3)
            return "C";
        else if (l >= 3 && l < 4)
            return "D";
        else if (l >= 4 && l < 5)
            return "E";
        else if (l >= 5)
            return "F";
        else
            return "";
    }

    public double getY(boolean mw, String cl) {
        if (mw) {
            if (cl.equals("A")) {
                return 0.32;
            } else if (cl.equals("B")) {
                return 0.32;
            } else if (cl.equals("C")) {
                return 0.22;
            } else if (cl.equals("D")) {
                return 0.16;
            } else if (cl.equals("E")) {
                return 0.11;
            } else if (cl.equals("F")) {
                return 0.11;
            } else {
                return 1.0;
            }
        } else {
            if (cl.equals("A")) {
                return 0.22;
            } else if (cl.equals("B")) {
                return 0.16;
            } else if (cl.equals("C")) {
                return 0.11;
            } else if (cl.equals("D")) {
                return 0.08;
            } else if (cl.equals("E")) {
                return 0.06;
            } else if (cl.equals("F")) {
                return 0.04;
            } else {
                return 1.0;
            }
        }
    }

    public double getZ(boolean mw, String cl) {
        if (mw) {
            if (cl.equals("A")) {
                return 0.24;
            } else if (cl.equals("B")) {
                return 0.24;
            } else if (cl.equals("C")) {
                return 0.20;
            } else if (cl.equals("D")) {
                return 0.14;
            } else if (cl.equals("E")) {
                return 0.08;
            } else if (cl.equals("F")) {
                return 0.08;
            } else {
                return 1.0;
            }
        } else {
            if (cl.equals("A")) {
                return 0.20;
            } else if (cl.equals("B")) {
                return 0.12;
            } else if (cl.equals("C")) {
                return 0.08;
            } else if (cl.equals("D")) {
                return 0.06;
            } else if (cl.equals("E")) {
                return 0.03;
            } else if (cl.equals("F")) {
                return 0.016;
            } else {
                return 1.0;
            }
        }
    }
}