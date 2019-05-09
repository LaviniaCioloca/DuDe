
/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

  ********

Wael Kombar - 27 Mars 1991
  Creation
*/
#ifndef CTOASSERT_H
#define CTOASSERT_H

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

#ifdef CTOASSERT

#define Cto_Assert(exp,mss)	\
	((void)((exp) ? 1 :	\
		Cto_AssertError(mss)))

#else /* CTOASSERT */

#define Cto_Assert(exp,mss)

#endif /* CTOASSERT */

#if defined(CTOASSERT) || defined(CTOASSERTI)

#define Cto_AssertI(exp,mss)	\
	((void)((exp) ? 1 :	\
		_Cto_AssertError(mss)))

#else /* CTOASSERTI */

#define Cto_AssertI(exp,mss)

#endif /* CTOASSERTI */

#define Cto_Check(exp,mss) \
	(((exp) ? (void)1 : \
		Cto_AssertError(mss)))

#define Cto_AssertError(mss) _Cto_AssertError(mss, __FILE__, __LINE__)
IMPORT 
void _Cto_AssertError(char *mss, char *file, int line);

#endif /* CTOASSERT_H */
