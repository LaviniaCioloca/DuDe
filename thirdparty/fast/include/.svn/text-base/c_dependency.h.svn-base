/*********
**********   ENVIRONNEMENT CONCERTO  C
**********   Gestion de la table des types
**********
* 
* Copyright Sema Group 1995 - 1995
* 
**********
* 
*  Jean-Marc Letteron - Octobre 1998
*   Librairies dynamiques
*/

#ifndef C_DEPENDENCY_H
#define C_DEPENDENCY_H

#include <stdio.h>
#include <tlist.h>
#include <vtp.h>

#undef FAST_H_DLL
#define FAST_H_DLL  ENV_C_LIB
#include <fastdll.h>

#define DEPGRAPH_NODES_MAP(graph, count, name, found, stop)         \
          { C_ModuleP _mod;                                           \
	    TLIST_MAP(graph, C_ModuleP, _mod);                        \
	      { count = _mod->count;                                \
		name = _mod->name;                                  \
                found = _mod->found;                                \
		stop = _mod->stop;

#define NO_LINK() (!TList_Length(_mod->includes))

#define NODE_LINKS_MAP(dest)                                       \
		{ C_ModuleP _child;                                  \
		  TLIST_MAP(_mod->includes, C_ModuleP, _child); {    \
                    dest = _child->count;

#define NODE_LINKS_END_MAP()                                       \
		  } TLIST_END_MAP();                               \
		}


#define DEPGRAPH_NODES_END_MAP()                                   \
	      } TLIST_END_MAP();                                   \
	   }

typedef struct C_ModuleS {
  char *name;
  int stop;
  int count;
  TListP include_names;
  TListP includes;
  TListP included_by;
  TListP types;
  int found;
  int visited;
} *C_ModuleP, C_ModuleS;

typedef TListP C_DependencyGraphP;

/* Calcule l'ordre de parsing correspondant au graphe d'inclusion 'graph' */
IMPORT TListP
C_DependencyOrder(C_DependencyGraphP graph, TListP ordered_list);

/* Calcule la liste ordonnee des fichiers dont depend 'filename'.
 * Il suffit de les parser dans cet ordre pour que les #includes de 'filename'
 * soient tous resolus.
 */
IMPORT TListP
C_IncludedFiles(char *filename, C_DependencyGraphP graph);

/* Renvoie la liste des fichiers inclus avant filename dans un fichier.
 * Un fichier est considere comme a inclure s'il existe un troisieme
 * fichier l'incluant avant d'inclure le fichier 'filename'.
 * Les tables des types de ces fichiers seront chargees avant de parser
 * filename.
 */
IMPORT TListP
C_DependencyContexts(char *filename, C_DependencyGraphP graph);

/* Calcule le graphe de dependance . Retour = C_DependencyGraphP*/
IMPORT C_DependencyGraphP
C_Get_DependencyGraph(TListP files, int (*contfct)(char *));

/* sauve au format graphe dans fp le graphe de dependance courant */
IMPORT void
C_Dump_DependencyGraph(FILE *fp, C_DependencyGraphP graph);

/* Libere le graphe de dependance courant. A appeler pour recalculer 
 * un nouveau graphe de dependance */
IMPORT void
C_Free_DependencyGraph(C_DependencyGraphP graph);

#endif /* C_DEPENDENCY_H */
