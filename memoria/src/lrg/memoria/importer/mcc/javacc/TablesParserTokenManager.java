/* Generated By:JavaCC: Do not edit this line. TablesParserTokenManager.java */
package lrg.memoria.importer.mcc.javacc;


public class TablesParserTokenManager implements TablesParserConstants {
    static void CommonTokenAction(Token t) {
        if (t.image.equals("\"\""))
            t.image = "";
    }

    public java.io.PrintStream debugStream = System.out;

    public void setDebugStream(java.io.PrintStream ds) {
        debugStream = ds;
    }

    private final int jjStopStringLiteralDfa_0(int pos, long active0) {
        switch (pos) {
            case 0:
                if ((active0 & 0x2L) != 0L)
                    return 1;
                if ((active0 & 0x1fe0L) != 0L) {
                    jjmatchedKind = 14;
                    return 1;
                }
                return -1;
            case 1:
                if ((active0 & 0x1fe0L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 1;
                    return 1;
                }
                return -1;
            case 2:
                if ((active0 & 0x1fe0L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 2;
                    return 1;
                }
                return -1;
            case 3:
                if ((active0 & 0x100L) != 0L)
                    return 1;
                if ((active0 & 0x1ee0L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 3;
                    return 1;
                }
                return -1;
            case 4:
                if ((active0 & 0x1ee0L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 4;
                    return 1;
                }
                return -1;
            case 5:
                if ((active0 & 0x1ee0L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 5;
                    return 1;
                }
                return -1;
            case 6:
                if ((active0 & 0x1ec0L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 6;
                    return 1;
                }
                if ((active0 & 0x20L) != 0L)
                    return 1;
                return -1;
            case 7:
                if ((active0 & 0x1e40L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 7;
                    return 1;
                }
                if ((active0 & 0x80L) != 0L)
                    return 1;
                return -1;
            case 8:
                if ((active0 & 0xa40L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 8;
                    return 1;
                }
                if ((active0 & 0x1400L) != 0L)
                    return 1;
                return -1;
            case 9:
                if ((active0 & 0x840L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 9;
                    return 1;
                }
                if ((active0 & 0x200L) != 0L)
                    return 1;
                return -1;
            case 10:
                if ((active0 & 0x840L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 10;
                    return 1;
                }
                return -1;
            case 11:
                if ((active0 & 0x840L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 11;
                    return 1;
                }
                return -1;
            case 12:
                if ((active0 & 0x840L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 12;
                    return 1;
                }
                return -1;
            case 13:
                if ((active0 & 0x840L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 13;
                    return 1;
                }
                return -1;
            case 14:
                if ((active0 & 0x800L) != 0L) {
                    jjmatchedKind = 14;
                    jjmatchedPos = 14;
                    return 1;
                }
                if ((active0 & 0x40L) != 0L)
                    return 1;
                return -1;
            default :
                return -1;
        }
    }

    private final int jjStartNfa_0(int pos, long active0) {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
    }

    private final int jjStopAtPos(int pos, int kind) {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        return pos + 1;
    }

    private final int jjStartNfaWithStates_0(int pos, int kind, int state) {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            return pos + 1;
        }
        return jjMoveNfa_0(state, pos + 1);
    }

    private final int jjMoveStringLiteralDfa0_0() {
        switch (curChar) {
            case 10:
                return jjStopAtPos(0, 4);
            case 34:
                return jjStartNfaWithStates_0(0, 1, 1);
            case 60:
                return jjMoveStringLiteralDfa1_0(0x1ee0L);
            case 78:
                return jjMoveStringLiteralDfa1_0(0x100L);
            default :
                return jjMoveNfa_0(2, 0);
        }
    }

    private final int jjMoveStringLiteralDfa1_0(long active0) {
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(0, active0);
            return 1;
        }
        switch (curChar) {
            case 69:
                return jjMoveStringLiteralDfa2_0(active0, 0x20L);
            case 73:
                return jjMoveStringLiteralDfa2_0(active0, 0x800L);
            case 78:
                return jjMoveStringLiteralDfa2_0(active0, 0x680L);
            case 79:
                return jjMoveStringLiteralDfa2_0(active0, 0x40L);
            case 85:
                return jjMoveStringLiteralDfa2_0(active0, 0x1100L);
            default :
                break;
        }
        return jjStartNfa_0(0, active0);
    }

    private final int jjMoveStringLiteralDfa2_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(0, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(1, active0);
            return 2;
        }
        switch (curChar) {
            case 76:
                return jjMoveStringLiteralDfa3_0(active0, 0x100L);
            case 78:
                return jjMoveStringLiteralDfa3_0(active0, 0x1840L);
            case 79:
                return jjMoveStringLiteralDfa3_0(active0, 0x680L);
            case 82:
                return jjMoveStringLiteralDfa3_0(active0, 0x20L);
            default :
                break;
        }
        return jjStartNfa_0(1, active0);
    }

    private final int jjMoveStringLiteralDfa3_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(1, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(2, active0);
            return 3;
        }
        switch (curChar) {
            case 73:
                return jjMoveStringLiteralDfa4_0(active0, 0x800L);
            case 75:
                return jjMoveStringLiteralDfa4_0(active0, 0x1000L);
            case 76:
                if ((active0 & 0x100L) != 0L)
                    return jjStartNfaWithStates_0(3, 8, 1);
                return jjMoveStringLiteralDfa4_0(active0, 0x40L);
            case 82:
                return jjMoveStringLiteralDfa4_0(active0, 0x20L);
            case 84:
                return jjMoveStringLiteralDfa4_0(active0, 0x200L);
            case 95:
                return jjMoveStringLiteralDfa4_0(active0, 0x480L);
            default :
                break;
        }
        return jjStartNfa_0(2, active0);
    }

    private final int jjMoveStringLiteralDfa4_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(2, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(3, active0);
            return 4;
        }
        switch (curChar) {
            case 78:
                return jjMoveStringLiteralDfa5_0(active0, 0x1400L);
            case 79:
                return jjMoveStringLiteralDfa5_0(active0, 0xa0L);
            case 84:
                return jjMoveStringLiteralDfa5_0(active0, 0x800L);
            case 89:
                return jjMoveStringLiteralDfa5_0(active0, 0x40L);
            case 95:
                return jjMoveStringLiteralDfa5_0(active0, 0x200L);
            default :
                break;
        }
        return jjStartNfa_0(3, active0);
    }

    private final int jjMoveStringLiteralDfa5_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(3, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(4, active0);
            return 5;
        }
        switch (curChar) {
            case 65:
                return jjMoveStringLiteralDfa6_0(active0, 0x400L);
            case 73:
                return jjMoveStringLiteralDfa6_0(active0, 0x200L);
            case 78:
                return jjMoveStringLiteralDfa6_0(active0, 0x80L);
            case 79:
                return jjMoveStringLiteralDfa6_0(active0, 0x1000L);
            case 82:
                return jjMoveStringLiteralDfa6_0(active0, 0x20L);
            case 95:
                return jjMoveStringLiteralDfa6_0(active0, 0x840L);
            default :
                break;
        }
        return jjStartNfa_0(4, active0);
    }

    private final int jjMoveStringLiteralDfa6_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(4, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(5, active0);
            return 6;
        }
        switch (curChar) {
            case 62:
                if ((active0 & 0x20L) != 0L)
                    return jjStartNfaWithStates_0(6, 5, 1);
                break;
            case 68:
                return jjMoveStringLiteralDfa7_0(active0, 0x40L);
            case 69:
                return jjMoveStringLiteralDfa7_0(active0, 0x80L);
            case 77:
                return jjMoveStringLiteralDfa7_0(active0, 0x400L);
            case 78:
                return jjMoveStringLiteralDfa7_0(active0, 0xa00L);
            case 87:
                return jjMoveStringLiteralDfa7_0(active0, 0x1000L);
            default :
                break;
        }
        return jjStartNfa_0(5, active0);
    }

    private final int jjMoveStringLiteralDfa7_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(5, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(6, active0);
            return 7;
        }
        switch (curChar) {
            case 62:
                if ((active0 & 0x80L) != 0L)
                    return jjStartNfaWithStates_0(7, 7, 1);
                break;
            case 69:
                return jjMoveStringLiteralDfa8_0(active0, 0x440L);
            case 73:
                return jjMoveStringLiteralDfa8_0(active0, 0x200L);
            case 78:
                return jjMoveStringLiteralDfa8_0(active0, 0x1000L);
            case 85:
                return jjMoveStringLiteralDfa8_0(active0, 0x800L);
            default :
                break;
        }
        return jjStartNfa_0(6, active0);
    }

    private final int jjMoveStringLiteralDfa8_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(6, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(7, active0);
            return 8;
        }
        switch (curChar) {
            case 62:
                if ((active0 & 0x400L) != 0L)
                    return jjStartNfaWithStates_0(8, 10, 1);
                else if ((active0 & 0x1000L) != 0L)
                    return jjStartNfaWithStates_0(8, 12, 1);
                break;
            case 67:
                return jjMoveStringLiteralDfa9_0(active0, 0x40L);
            case 76:
                return jjMoveStringLiteralDfa9_0(active0, 0x800L);
            case 84:
                return jjMoveStringLiteralDfa9_0(active0, 0x200L);
            default :
                break;
        }
        return jjStartNfa_0(7, active0);
    }

    private final int jjMoveStringLiteralDfa9_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(7, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(8, active0);
            return 9;
        }
        switch (curChar) {
            case 62:
                if ((active0 & 0x200L) != 0L)
                    return jjStartNfaWithStates_0(9, 9, 1);
                break;
            case 76:
                return jjMoveStringLiteralDfa10_0(active0, 0x840L);
            default :
                break;
        }
        return jjStartNfa_0(8, active0);
    }

    private final int jjMoveStringLiteralDfa10_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(8, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(9, active0);
            return 10;
        }
        switch (curChar) {
            case 65:
                return jjMoveStringLiteralDfa11_0(active0, 0x40L);
            case 95:
                return jjMoveStringLiteralDfa11_0(active0, 0x800L);
            default :
                break;
        }
        return jjStartNfa_0(9, active0);
    }

    private final int jjMoveStringLiteralDfa11_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(9, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(10, active0);
            return 11;
        }
        switch (curChar) {
            case 66:
                return jjMoveStringLiteralDfa12_0(active0, 0x800L);
            case 82:
                return jjMoveStringLiteralDfa12_0(active0, 0x40L);
            default :
                break;
        }
        return jjStartNfa_0(10, active0);
    }

    private final int jjMoveStringLiteralDfa12_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(10, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(11, active0);
            return 12;
        }
        switch (curChar) {
            case 69:
                return jjMoveStringLiteralDfa13_0(active0, 0x40L);
            case 79:
                return jjMoveStringLiteralDfa13_0(active0, 0x800L);
            default :
                break;
        }
        return jjStartNfa_0(11, active0);
    }

    private final int jjMoveStringLiteralDfa13_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(11, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(12, active0);
            return 13;
        }
        switch (curChar) {
            case 68:
                return jjMoveStringLiteralDfa14_0(active0, 0x840L);
            default :
                break;
        }
        return jjStartNfa_0(12, active0);
    }

    private final int jjMoveStringLiteralDfa14_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(12, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(13, active0);
            return 14;
        }
        switch (curChar) {
            case 62:
                if ((active0 & 0x40L) != 0L)
                    return jjStartNfaWithStates_0(14, 6, 1);
                break;
            case 89:
                return jjMoveStringLiteralDfa15_0(active0, 0x800L);
            default :
                break;
        }
        return jjStartNfa_0(13, active0);
    }

    private final int jjMoveStringLiteralDfa15_0(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(13, old0);
        try {
            curChar = input_stream.readChar();
        } catch (java.io.IOException e) {
            jjStopStringLiteralDfa_0(14, active0);
            return 15;
        }
        switch (curChar) {
            case 62:
                if ((active0 & 0x800L) != 0L)
                    return jjStartNfaWithStates_0(15, 11, 1);
                break;
            default :
                break;
        }
        return jjStartNfa_0(14, active0);
    }

    private final void jjCheckNAdd(int state) {
        if (jjrounds[state] != jjround) {
            jjstateSet[jjnewStateCnt++] = state;
            jjrounds[state] = jjround;
        }
    }

    private final void jjAddStates(int start, int end) {
        do {
            jjstateSet[jjnewStateCnt++] = jjnextStates[start];
        } while (start++ != end);
    }

    private final void jjCheckNAddTwoStates(int state1, int state2) {
        jjCheckNAdd(state1);
        jjCheckNAdd(state2);
    }

    private final void jjCheckNAddStates(int start, int end) {
        do {
            jjCheckNAdd(jjnextStates[start]);
        } while (start++ != end);
    }

    private final void jjCheckNAddStates(int start) {
        jjCheckNAdd(jjnextStates[start]);
        jjCheckNAdd(jjnextStates[start + 1]);
    }

    static final long[] jjbitVec0 = {
        0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
    };

    private final int jjMoveNfa_0(int startState, int curPos) {
        int[] nextStates;
        int startsAt = 0;
        jjnewStateCnt = 2;
        int i = 1;
        jjstateSet[0] = startState;
        int j, kind = 0x7fffffff;
        for (; ;) {
            if (++jjround == 0x7fffffff)
                ReInitRounds();
            if (curChar < 64) {
                long l = 1L << curChar;
                MatchLoop: do {
                    switch (jjstateSet[--i]) {
                        case 2:
                            if ((0xffffffffffffd9ffL & l) != 0L) {
                                if (kind > 14)
                                    kind = 14;
                                jjCheckNAdd(1);
                            }
                            if ((0x3ff000000000000L & l) != 0L) {
                                if (kind > 13)
                                    kind = 13;
                                jjCheckNAdd(0);
                            }
                            break;
                        case 0:
                            if ((0x3ff000000000000L & l) == 0L)
                                break;
                            if (kind > 13)
                                kind = 13;
                            jjCheckNAdd(0);
                            break;
                        case 1:
                            if ((0xffffffffffffd9ffL & l) == 0L)
                                break;
                            if (kind > 14)
                                kind = 14;
                            jjCheckNAdd(1);
                            break;
                        default :
                            break;
                    }
                } while (i != startsAt);
            } else if (curChar < 128) {
                long l = 1L << (curChar & 077);
                MatchLoop: do {
                    switch (jjstateSet[--i]) {
                        case 2:
                        case 1:
                            kind = 14;
                            jjCheckNAdd(1);
                            break;
                        default :
                            break;
                    }
                } while (i != startsAt);
            } else {
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                MatchLoop: do {
                    switch (jjstateSet[--i]) {
                        case 2:
                        case 1:
                            if ((jjbitVec0[i2] & l2) == 0L)
                                break;
                            if (kind > 14)
                                kind = 14;
                            jjCheckNAdd(1);
                            break;
                        default :
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff) {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 2 - (jjnewStateCnt = startsAt)))
                return curPos;
            try {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e) {
                return curPos;
            }
        }
    }

    static final int[] jjnextStates = {
    };
    public static final String[] jjstrLiteralImages = {
        "", null, null, null, "\12", "\74\105\122\122\117\122\76",
        "\74\117\116\114\131\137\104\105\103\114\101\122\105\104\76", "\74\116\117\137\117\116\105\76", "\116\125\114\114",
        "\74\116\117\124\137\111\116\111\124\76", "\74\116\117\137\116\101\115\105\76",
        "\74\111\116\111\124\137\116\125\114\114\137\102\117\104\131\76", "\74\125\116\113\116\117\127\116\76", null, null, };
    public static final String[] lexStateNames = {
        "DEFAULT",
    };
    static final long[] jjtoToken = {
        0x7ff1L,
    };
    static final long[] jjtoSkip = {
        0xeL,
    };
    protected SimpleCharStream input_stream;
    private final int[] jjrounds = new int[2];
    private final int[] jjstateSet = new int[4];
    protected char curChar;

    public TablesParserTokenManager(SimpleCharStream stream) {
        if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
        input_stream = stream;
    }

    public TablesParserTokenManager(SimpleCharStream stream, int lexState) {
        this(stream);
        SwitchTo(lexState);
    }

    public void ReInit(SimpleCharStream stream) {
        jjmatchedPos = jjnewStateCnt = 0;
        curLexState = defaultLexState;
        input_stream = stream;
        ReInitRounds();
    }

    private final void ReInitRounds() {
        int i;
        jjround = 0x80000001;
        for (i = 2; i-- > 0;)
            jjrounds[i] = 0x80000000;
    }

    public void ReInit(SimpleCharStream stream, int lexState) {
        ReInit(stream);
        SwitchTo(lexState);
    }

    public void SwitchTo(int lexState) {
        if (lexState >= 1 || lexState < 0)
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
        else
            curLexState = lexState;
    }

    protected Token jjFillToken() {
        Token t = Token.newToken(jjmatchedKind);
        t.kind = jjmatchedKind;
        String im = jjstrLiteralImages[jjmatchedKind];
        t.image = (im == null) ? input_stream.GetImage() : im;
        t.beginLine = input_stream.getBeginLine();
        t.beginColumn = input_stream.getBeginColumn();
        t.endLine = input_stream.getEndLine();
        t.endColumn = input_stream.getEndColumn();
        return t;
    }

    int curLexState = 0;
    int defaultLexState = 0;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;

    public Token getNextToken() {
        int kind;
        Token specialToken = null;
        Token matchedToken;
        int curPos = 0;

        EOFLoop :
        for (; ;) {
            try {
                curChar = input_stream.BeginToken();
            } catch (java.io.IOException e) {
                jjmatchedKind = 0;
                matchedToken = jjFillToken();
                CommonTokenAction(matchedToken);
                return matchedToken;
            }

            try {
                input_stream.backup(0);
                while (curChar <= 13 && (0x2200L & (1L << curChar)) != 0L)
                    curChar = input_stream.BeginToken();
            } catch (java.io.IOException e1) {
                continue EOFLoop;
            }
            jjmatchedKind = 0x7fffffff;
            jjmatchedPos = 0;
            curPos = jjMoveStringLiteralDfa0_0();
            if (jjmatchedKind != 0x7fffffff) {
                if (jjmatchedPos + 1 < curPos)
                    input_stream.backup(curPos - jjmatchedPos - 1);
                if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
                    matchedToken = jjFillToken();
                    CommonTokenAction(matchedToken);
                    return matchedToken;
                } else {
                    continue EOFLoop;
                }
            }
            int error_line = input_stream.getEndLine();
            int error_column = input_stream.getEndColumn();
            String error_after = null;
            boolean EOFSeen = false;
            try {
                input_stream.readChar();
                input_stream.backup(1);
            } catch (java.io.IOException e1) {
                EOFSeen = true;
                error_after = curPos <= 1 ? "" : input_stream.GetImage();
                if (curChar == '\n' || curChar == '\r') {
                    error_line++;
                    error_column = 0;
                } else
                    error_column++;
            }
            if (!EOFSeen) {
                input_stream.backup(1);
                error_after = curPos <= 1 ? "" : input_stream.GetImage();
            }
            throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
        }
    }

}
