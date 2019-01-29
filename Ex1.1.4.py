# -*- coding: utf-8 -*-
#!/usr/bin/env python3
import math
class Cercle:
    """
	    Classe définissant un cercle caractérisé par :
        - son rayon
        - son aire
        - son périmètre
	"""

    
    def __init__(self, rayon):
        """
		    Constructeur de classe
	    """
        self.rayon = rayon
        
    def perimetre(self):
	    return 2 * self.rayon * math.pi
	
    def aire(self):
	    return self.rayon * self.rayon * math.pi

