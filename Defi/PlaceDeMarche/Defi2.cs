pragma solidity ^0.5.4;

import "github.com/OpenZeppelin/openzeppelin-solidity/contracts/math/SafeMath.sol";

/** @title Gestion des Entites */
contract GestionEntites{
    
    struct Illustrateur{
        string name;
        uint reputation;
    }
    
    struct Entreprise{
        string nomEntreprise;
        uint reputation;
    }
    
    mapping (address => Illustrateur) public illustrateurs;
    address[] public adrIllustrateur;
    
    mapping (address => Entreprise) public entreprises;
    address[] public adrEntreprises;
    
    address[] internal administrateurs;
    address payable internal tresorier;
    
    address[] private illustrateursBannis;
    address[] private entreprisesBannis;
    
    /** @dev Inscription d'un illustrateur.
      * @param _name Nom de l'illustrateur.
      */
    function inscriptionIllustrateur(string memory _name) public {
        require(!illustrateurBanni());
        require(!estIllustrateur(msg.sender));
        illustrateurs[msg.sender] = Illustrateur({name : _name, reputation : 1});
        adrIllustrateur.push(msg.sender);
    }
    
    /** @dev Inscription d'une entreprise.
      * @param _name Nom de l'entreprise.
      */
    function inscriptionEntreprise(string memory _name) public {
        require(!entrepriseBanni());
        require(!estEntreprise(msg.sender));
        entreprises[msg.sender] = Entreprise({nomEntreprise : _name, reputation : 1});
        adrEntreprises.push(msg.sender);
    }
    
    /** @dev Bannir un illustrateur.
      * @param adresse de l'illustrateur.
      */
    function bannirIllustrateur(address adresse) public {
        require(estAdmin());
        require(estIllustrateur(adresse));
        delete illustrateurs[adresse];
        illustrateursBannis.push(adresse);
        uint nbrIllustrateurs = adrIllustrateur.length;
        for (uint i = 0; i < nbrIllustrateurs; i++){
            if(adresse == adrIllustrateur[i]){
                adrIllustrateur[i] = adrIllustrateur[nbrIllustrateurs-1];
                delete adrIllustrateur[nbrIllustrateurs-1];
                break;
            } 
        } 
    }
    
    /** @dev Bannir une entreprise.
      * @param adresse de l'entreprise.
      */
    function bannirEntreprise(address adresse) public {
        require(estAdmin());
        require(estEntreprise(adresse));
        delete entreprises[adresse];
        entreprisesBannis.push(adresse);
        uint nbrEntreprises = adrIllustrateur.length;
        for (uint i = 0; i < nbrEntreprises; i++){
            if(adresse == adrIllustrateur[i]){
                adrIllustrateur[i] = adrIllustrateur[nbrEntreprises-1];
                delete adrIllustrateur[nbrEntreprises-1];
                break;
            } 
        } 
    }
    
    /** @dev Verifie si l'illustrateur n'est pas banni.
      * @return true si l'illustrateur est banni.
      */
    function illustrateurBanni() internal view returns(bool){
        uint nbrBanni = illustrateursBannis.length;
        for (uint i = 0; i < nbrBanni ; i++) if (msg.sender == illustrateursBannis[i]) return true;
    }
    
    /** @dev Verifie si l'entreprise n'est pas banni.
      * @return true si l'entreprise est banni.
      */
    function entrepriseBanni() internal view returns(bool){
        uint nbrBanni = entreprisesBannis.length;
        for (uint i = 0; i < nbrBanni ; i++) if (msg.sender == entreprisesBannis[i]) return true;
    }
    
    /** @dev Verifie que l'utilisateur est admin.
      * @return true si l'utilisateur est admin.
      */
    function estAdmin() internal view returns(bool){
        uint nbrAdmin = administrateurs.length;
        for (uint i = 0; i < nbrAdmin; i++) if(msg.sender == administrateurs[i]) return true;
    }
    
    /** @dev Verifie que l'utilisateur est illustrateur.
      * @return true si l'utilisateur est illustrateur.
      */
    function estIllustrateur(address adresse) internal view returns(bool){
        uint nbrIllustrateur = adrIllustrateur.length;
        for (uint i = 0; i < nbrIllustrateur; i++) if(adresse == adrIllustrateur[i]) return true;
    }
    
    /** @dev Verifie que l'utilisateur est une entreprise.
      * @return true si l'utilisateur est une entreprise.
      */
    function estEntreprise(address adresse) internal view returns(bool){
        uint nbrEntreprise = adrEntreprises.length;
        for ( uint i = 0; i < nbrEntreprise; i++) if(adresse == adrEntreprises[i]) return true;
    }
    
    
}
/** @title OffresDeServices  */
contract OffresDeServices is GestionEntites{
    // les différents statut d'une demande
    enum Statut { Ouverte, Encours, Fermee }
    
    // la structure d'une demande
    struct Demande {
        Statut statut; // statu de la demande
        address adresseDemandeur; // adresse de l'entreprise qui a fait la demande
        uint delai; // delai nécessaire pour réaliser la demande en jour, passe en deadline au statut Encours 
        string projet; // description du projet 
        uint reputation; // réputation minimum nécessaire pour postuler
        uint remuneration; // rémunération en wei
        bytes32 urlDepot; // hash de l'url ou le projet est déposé 
        address payable candidatRetenu; // candidat retenu
        address[] adresseCandidats; // liste des candidats
        mapping(address => bool) candidats; // candidats sélectionné par l'entreprise
    }
    
    Demande[] public demandes; 
    
    /** @dev accède à l'entier length de demandes.
      * @return le nombre de demande dans le tableau demandes.
      */
    function nombreDemandes() public view returns(uint){return demandes.length; }
    
    /** @dev init le contrat.
      * @param _tresorier l'adresse qui récupère les frais  
      */
    constructor(address payable _tresorier) public {
        administrateurs.push(msg.sender);
        tresorier = _tresorier;
    }
    
    /** @dev vérifie le statut d'une demande.
      * @param demande l'index de la demande
      * @param statut de la demande à vérifier
      */
    modifier demandeStatut(uint demande, Statut statut) {
        require(demandes[demande].statut == statut, "Statut Invalide ! ");
        _;
    }
    
    /** @dev ajout  d'une demande.
      * @param _projet description de la demande
      * @param _delai nécessaire pour effectuer la demande
      * @param _reputation nécessaire pour postuler 
      * @return la position de la demande
      */
    function ajouterDemande(string memory _projet, uint _delai, uint _reputation) public payable returns (uint)  {
        require(estEntreprise(msg.sender));
        require(msg.value >= 100 wei); // 100 wei minimum pour pouvoir calculer les frais 
        uint frais = SafeMath.div(SafeMath.mul(msg.value, 2), 100); // calcule des frais
        tresorier.transfer(frais); // transfert des frais
        demandes.push(Demande({ adresseDemandeur : msg.sender,
                                statut : Statut.Ouverte, 
                                delai : _delai,
                                projet : _projet,
                                reputation : _reputation,
                                remuneration : msg.value - frais,
                                urlDepot : bytes32(0),
                                candidatRetenu : address(0),
                                adresseCandidats : new address[](0)}));
        return demandes.length - 1;
    }
    
    /** @dev accepter un candidat pour une demande.
      * @param demande l'index de la demande
      * @param candidat accepté
      */
    function accepterUnCandidat(uint demande, uint candidat) public demandeStatut(demande, Statut.Ouverte){
        require(demandes[demande].adresseDemandeur == msg.sender);
        require(demandes[demande].adresseCandidats.length >= candidat);
        demandes[demande].candidats[demandes[demande].adresseCandidats[candidat]] = true;
    }
    
    /** @dev cloture une demande, débloque la rémunération et augmente la réputation des parties
      * @param demande l'index de la demande
      */
    function cloturerDemande(uint demande) public demandeStatut(demande, Statut.Encours){
        require(demandes[demande].adresseDemandeur == msg.sender, "Cette demande ne vous appartien pas");
        demandes[demande].statut = Statut.Fermee;
        demandes[demande].candidatRetenu.transfer(demandes[demande].remuneration);
        illustrateurs[demandes[demande].candidatRetenu].reputation++;
        entreprises[msg.sender].reputation++;
    }
    
    /** @dev postuler pour une demande
      * @param demande l'index de la demande
      */
    function postulerPourDemande(uint demande) public demandeStatut(demande, Statut.Ouverte){
        require(!illustrateurBanni());
        require(illustrateurs[msg.sender].reputation >= demandes[demande].reputation);
        demandes[demande].adresseCandidats.push(msg.sender);
    }

    /** @dev confirmer la demande validé par l'entreprises
      * @param demande l'index de la demande
      */
    function comfirmationDuCandidat(uint demande) public demandeStatut(demande, Statut.Ouverte){
        require(demandes[demande].candidats[msg.sender] == true);
        demandes[demande].statut = Statut.Encours;
        demandes[demande].candidatRetenu = msg.sender;
        demandes[demande].delai = SafeMath.mul(demandes[demande].delai, 1 days) + now;
    }
    
    /** @dev Hash et livre l'url du dépôt
      * @param demande l'index de la demande
      * @param url du dépôt
      */
    function livraisonURL(uint demande, string memory url) public demandeStatut(demande, Statut.Encours){
        require(demandes[demande].candidatRetenu == msg.sender);
        demandes[demande].urlDepot = keccak256(abi.encodePacked(url));
    }
}

























