#!/usr/bin/env python3 
# -*- coding: utf-8 -*-

class CourbeElliptique:


    def __init__(self, a, b):
      
	  
        self.a = a
        self.b = b
	  
        if 4*a**3+27*b**2 == 0 :
            raise ValueError('({}, {}) n\'est pas une courbe valide'.format(a, b))
            
        
 
    def __eq__(self, equi):
        return (self.a, self.b) == (equi.a, equi.b)      
 
    def testPoint(self, x, y):
        return y*y == x*x*x + self.a * x + self.b
 
    def __str__(self):
        return 'y^2 = x^3 + %Gx + %G' % (self.a, self.b)

		
