# -*- coding: utf-8 -*-
#!/usr/bin/env python3 
liste_octet = [2000, 6000, 800, 700, 1200, 1000, 1300, 600]
liste_ratioRentabilite = []
liste_pourboire = [13000, 9000, 2000, 1500, 3500, 2800, 5000, 1500]

complexite = 0
idRatioRent = 0 
idTriRatioRent = 0 
 
while idRatioRent < len(liste_octet):               #On rempli la liste des ratio de rentabilité (nbr de satoshis par octet)
    
    complexite += 1
    ratio = liste_pourboire[idRatioRent]/liste_octet[idRatioRent]
    liste_ratioRentabilite.append(ratio)
    idRatioRent += 1

while idTriRatioRent < len(liste_ratioRentabilite): # on trie les ratio de rentabilité par grandeur

    complexite += 1
    idPG = idTriRatioRent
    
    c = idTriRatioRent
    while c < len(liste_ratioRentabilite):
        
        complexite += 1
        if liste_ratioRentabilite[idPG] < liste_ratioRentabilite[c] :
            idPG = c
            
        c += 1
    
    if idPG != idTriRatioRent: # on trie dans le m^me ordre les satoshis et octets
    
        liste_octet[idTriRatioRent], liste_octet[idPG] = liste_octet[idPG], liste_octet[idTriRatioRent]
        liste_ratioRentabilite[idTriRatioRent], liste_ratioRentabilite[idPG] = liste_ratioRentabilite[idPG], liste_ratioRentabilite[idTriRatioRent],
        liste_pourboire[idTriRatioRent], liste_pourboire[idPG] = liste_pourboire[idPG], liste_pourboire[idTriRatioRent]
       
    idTriRatioRent += 1
    
print("liste des ratios de rentabilité", liste_ratioRentabilite) # Affichage des trois listes
print("Liste des octets    ", liste_octet)
print("Liste des pourboire ", liste_pourboire)

pbr = 0 
liste_transaction = []
maxOctets = 0
idSomOctets = 0
while idSomOctets < len(liste_octet): # calcul de la somme des octets par ordre de rentabilité
    
    complexite += 1
    if maxOctets + liste_octet[idSomOctets] < 6000:
        
        maxOctets += liste_octet[idSomOctets]
        liste_transaction.append(liste_octet[idSomOctets])
        pbr += liste_pourboire[idSomOctets]
        
    idSomOctets += 1
    
print("Le total de pourboire est de", pbr, "satoshis pour la somme Max de", maxOctets, "octets avec les transactions", liste_transaction)
print("La Complexité est de ", complexite)   
    