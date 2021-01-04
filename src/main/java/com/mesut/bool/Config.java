package com.mesut.bool;

public class Config {
    public static NotMode notMode = NotMode.QUOTE;
    public static AndMode andMode = AndMode.DOT;
    public static OrMode orMode = OrMode.PLUS;
    public static XorMode xorMode = XorMode.POW;

    public enum NotMode {
        QUOTE, BANG, TILDE, STR;
    }

    public enum AndMode {
        DOT, STAR, AMP, STR;

        public String str() {
            if (this == DOT) {
                return ".";
            }
            else if (this == STAR) {
                return "*";
            }
            else if (this == AMP) {
                return "&";
            }
            return " and ";
        }
    }

    public enum OrMode {
        PLUS, AMP, BAR, STR;

        public String str() {
            if (this == PLUS) {
                return "+";
            }
            else if (this == AMP) {
                return "&";
            }
            else if (this == BAR) {
                return "|";
            }
            return " or ";
        }
    }

    public enum XorMode {
        POW, STR;

        public String str() {
            if (this == POW) {
                return "^";
            }
            return " xor ";
        }
    }


}
