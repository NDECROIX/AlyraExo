# -*- coding: utf-8 -*-
 
import os

print("\nSaisissez votre palindrome : ") 

testPalindrome = input()

testPalindrome.lower() #on met toutes les lettres en minuscule

#on fait un tableau avec les caratères à retirer
caratereIndesirable = (' ', '.', '?', '!', ':', ';', '-', '—', '(', ')', '[', ']', '’', '“', '”', '/', ',', '"') 
#supprimer chaque caractère indéirable du texte
for i in caratereIndesirable:
    testPalindrome = testPalindrome.replace(i, '')


#condition qui vérifie si c'est un palindrome en le comparant à son inverse
if testPalindrome == testPalindrome[::-1]: 
	print("C'est un palindrome")
else:
    print("Ce n'ai pas un palindrome")
	

os.system("pause")
