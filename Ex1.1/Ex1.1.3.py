# -*- coding: utf-8 -*-
#!/usr/bin/env python3 


def normalisationChaine(chainePalindrome):
    """
        On normalise la chaine saisi par l'utulisateur
    """
    
    chainePalindrome.lower()  #on met toutes les lettres en minuscule

	#on fait une liste avec les caratères à retirer
    caratereIndesirable = (' ', '.', '?', '!', ':', ';', '-', '—', '(', ')', '[', ']', '’', '“', '”', '/', ',', '"') 
	
	#supprimer chaque caractère indéirable du texte
    for i in caratereIndesirable:
	    chainePalindrome = chainePalindrome.replace(i, '')
	    
    return chainePalindrome
	    
    
	
def verifPalindrome(palindrome):
    """
       Fonction qui valide ou non le palindrome
    """
    
	#condition qui vérifie si c'est un palindrome en le comparant à son inverse
    if palindrome == palindrome[::-1]: 
        print("C'est un palindrome")
		
    else:
        print("Ce n'ai pas un palindrome")
		



print("\nSaisissez votre palindrome : ") 

saisiPalindrome = input()

verifPalindrome(normalisationChaine(saisiPalindrome))
