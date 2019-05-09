/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

**********

 Wael Kombar - 29 decembre 1993
    creation
*/

#ifndef _CTO_STDCTO_H
#define _CTO_STDCTO_H

#ifdef WIN32PC_NT
#define WIN32PC	1
#endif


#ifdef VAXVMS

/****
  Parce que le preprocesseur VMS ne reconnait pas l'inclusion par rapport
  au repertoire courant au moyen des ""
****/
#include <$ORIGIN_ctokernel:[include.ctokernel]ctomisc.h>
#include <$ORIGIN_ctokernel:[include.ctokernel]ctoassert.h>
#include <$ORIGIN_ctokernel:[include.ctokernel]exception.h>
#include <$ORIGIN_ctokernel:[include.ctokernel]ctoalloc.h>

#else /* include a la norme ANSI */

#include "ctokernel/ctomisc.h"
#include "ctokernel/ctoassert.h"
#include "ctokernel/exception.h"
#include "ctokernel/ctoalloc.h"

#endif /* Specificites VMS */

#endif
