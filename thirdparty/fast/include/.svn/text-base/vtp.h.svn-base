#ifndef _VTP_H
#define _VTP_H

#include <stdio.h>
#include <tlist.h>
#include <ctoio.h>

#ifdef VAXVMS

/****
  Because on VMS, include starts from the shell current directory,
  and not from the directory of the including file
****/
#include <$ORIGIN_vtp:[include.vtpkit]atoms.h>
#include <$ORIGIN_vtp:[include.vtpkit]formalism.h>
#include <$ORIGIN_vtp:[include.vtpkit]tree.h>

#else

#include "vtpkit/atoms.h"
#include "vtpkit/formalism.h"
#include "vtpkit/tree.h"

#endif /* VAXVMS */

#undef FAST_H_DLL
#define FAST_H_DLL VTPKIT_LIB
#include <fastdll.h>

Exception_Declare(EX_VTP);
Exception_Declare(EX_VTP_TREE);
Exception_Declare(EX_VTP_TREE_SHARED);
Exception_Declare(EX_VTP_TREE_NO_PARENT);
Exception_Declare(EX_VTP_TREE_IS_ATOMIC);
Exception_Declare(EX_VTP_TREE_NOT_LIST);
Exception_Declare(EX_VTP_TREE_RANK);
Exception_Declare(EX_VTP_OP_MISMATCH);
Exception_Declare(EX_VTP_FILE);		/* => file name */

IMPORT GLOBALREF VTP_FrameP	vtp_frame_coord;
IMPORT 
void	VTP_Init(void);

#endif /* _VTP_H */
