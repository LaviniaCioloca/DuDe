package lrg.memoria.importer.recoder;

import recoder.abstraction.Member;

public class RecoderToMemojConverter {

    public static int convertAccessMode(Member member) {
        if (member.isPublic())
            return lrg.memoria.core.AccessMode.PUBLIC;
        if (member.isProtected())
            return lrg.memoria.core.AccessMode.PROTECTED;
        if (member.isPrivate())
            return lrg.memoria.core.AccessMode.PRIVATE;
        return lrg.memoria.core.AccessMode.PACKAGE;
    }

    /*public static int convertAccessMode(MemberDeclaration md) {
        if (md.isPublic())
            return lrg.memoria.lrg.insider.lrg.insider.core.AccessMode.PUBLIC;
        if (md.isProtected())
            return lrg.memoria.lrg.insider.lrg.insider.core.AccessMode.PROTECTED;
        if (md.isPrivate())
            return lrg.memoria.lrg.insider.lrg.insider.core.AccessMode.PRIVATE;
        return lrg.memoria.lrg.insider.lrg.insider.core.AccessMode.PACKAGE;
    } */

}




