#!/usr/bin/env python3 
# -*- coding: utf-8 -*-

def meilleurPBR(W, octets, satoshis, n): 
    """
        fonction qui retourne le maximum de satoshis pour un nombre Max d'octets
        en fonction des disponibilités dans la liste passé en paramètre
    """
    
    K = [[0 for x in range(W+1)] for x in range(n+1)] 
    
    for i in range(n+1): 
        
        for w in range(W+1): 
            
            if i==0 or w==0: 
                K[i][w] = 0
                
            elif octets[i-1] <= w:  
                K[i][w] = max(satoshis[i-1] + K[i-1][w-octets[i-1]],  K[i-1][w]) 
				
              # si l'octets actuel est inférieur ou égale à la colone actuel
              # l'on renseigne dans la cellule K[i][w] la valeur max entre la valeur de la cellule du dessu K[i-1][w]
              # ou la valeur de l'octet actuel plus celui de la ligne au-dessus moins octets[i-1] colonne 
               
            else: 
                K[i][w] = K[i-1][w] # sinon on assigne la valeur du dessu
                
                
    ressourcesSatoshis = K[n][W]
		
    lysteTransaction = []
    lysteSatoshis = []
    
    ressourcesOctets = W
    for i in range(n, 0, -1): #on par de la fin du tableau
        
        if ressourcesSatoshis <= 0:
            break
		
        if ressourcesSatoshis == K[i - 1][ressourcesOctets]: #si la valeur des satoshi et la même à K[i - 1][w] on continue 
            continue
		
        else: # l'on soustrait les satoshis aux ressourcesSatoshis et les octets aux ressourcesOctets
			
            lysteTransaction.append(octets[i-1])
            lysteSatoshis.append(satoshis[i-1])
            
            ressourcesSatoshis = ressourcesSatoshis - satoshis[i - 1]
            ressourcesOctets = ressourcesOctets - octets[i - 1]
            
    print("Les transactions nécessaire pour atteindre le plus haut rendement de satoshis sont :", lysteTransaction )
    print("Pour un totale de ", K[n][W], "satoshis, par la somme des valeurs suivante : ", lysteSatoshis) 
	
satoshis = [13000, 9000, 2000, 1500, 3500, 2800, 5000, 1500]
octets   = [2000,  6000, 800,  700,  1200, 1000, 1300, 600 ]
W = 6000
n = len(satoshis) 

meilleurPBR(W, octets, satoshis, n)

print ("On a une complexité de O(nW) pour n le nombre de valeurs et W la capacité max du bloc d'octets. ")