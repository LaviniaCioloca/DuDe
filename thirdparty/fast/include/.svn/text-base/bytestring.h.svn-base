
/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

  ********
*/
#ifndef BYTESTRING_H
#define BYTESTRING_H

#include <string.h>
#include "hash.h"

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

/* Le type ByteString pour des chaines de caractere pouvant contenir */
/* des 0 */
typedef struct
    {
    size_t		size;       /* Taille de la chaine */
    unsigned char	*bytes;     /* Contenu */
    } ByteStringS, *ByteStringP;

/* liberation du contenu (bstr : ByteStringP)
 * A n'appeler que si le champ bytes a ete alloue par un Mem_AllocSize
 * dont la taille est le champ size
 */
#define ByteString_Free(bstr)\
  if ((bstr)->bytes) { Mem_FreeSize((bstr)->bytes, (bstr)->size); }

IMPORT GLOBALREF Hash_KeyClass hash_KeyByteString;

IMPORT GLOBALREF Hash_ValueClass hash_ValueByteString;
IMPORT 
void ByteString_FromString(ByteStringP bs, char *str);
IMPORT void ByteString_FromAnything(ByteStringP bs, VoidP buf, size_t buf_size);
IMPORT ByteStringP ByteString_Copy(ByteStringP bs);
IMPORT int ByteString_Compare(ByteStringP bs1, ByteStringP bs2);
IMPORT void ByteString_Delete(ByteStringP bs);

#endif /* BYTESTRING_H */
