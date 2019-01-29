
# -*- coding: utf-8 -*-
#!/usr/bin/env python3 


from random import randrange

def solutionUtulisateur():
	""" 
		Cette fonction demande à l'utulisateur de rentrer une valeur comprise entre 1 et 100
		et ainsi permettre au programme de le trouver
	"""
	
	#on test le nombre saisi
	error = True
	
	while error:
			
			print ("entrer un nombre compris entre 1 et 100")
			
			solution = input()
			
			try: # On essaye de le convertir en entier
			
				solution = int(solution)
				
				if solution > 99 or solution < 1:
						print("Vous devez saisir un nombre compris entre 1 et 100")
						
				else:
					error = False
					
			except ValueError:
				print("Vous n'avez pas saisi un nombre !")
	
	return solution
				

def division(nbrADiv):
	"""
	    Cette fonction divise un nombre par deux et renvoie un entier 
	"""
	
	return nbrADiv/2 - nbrADiv/2%1
				
def estimationProg(nbr):
	"""
	    Fonction qui trouve de façon efficace le nombre de l'utulisateur 
	"""
	
	estimation = 50 
	introuve = True
	minRange = 1
	maxRange = 100
	
	while introuve:
	
		print("Estimation du nombre :", int(estimation)) 
		
		if estimation < nbr:								#si la valeur est estimée inférieur on la garde comme valeur Min
			minRange = estimation							
			estimation = division(estimation+maxRange)		#on calcul une valeur supérieur
			
		elif estimation > nbr:								#si la valeur est estimée supérieur on la garde comme valeur Max
			maxRange = estimation
			estimation = division(estimation+minRange)		#on calcul une valeur inférieur
			
		else:
		    print("\n ################################# \n ### Votre nombre est le : ", int(estimation), "!!!\n #################################") 
		    introuve = False


estimationProg(solutionUtulisateur())
