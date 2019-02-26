pragma solidity ^0.5.4;


import "github.com/OpenZeppelin/openzeppelin-solidity/contracts/math/SafeMath.sol";


contract RessourcesHumaines{
    
    struct Illustrateur{
        string name;
        uint reputation;
    }
    
    struct Entreprise{
        address adresse;
        string nomEntreprise;
    }
    
    mapping (address => Illustrateur) public illustrateurs;
    
    Entreprise[] public entreprises;
    
    address[] internal administrateurs;
    address payable internal tresorier;
    
    address[] private bannis;
    
    function inscriptionIllustrateur(string memory _name) public {
        require(!banni());
        require(illustrateurs[msg.sender].reputation == 0);
        illustrateurs[msg.sender] = Illustrateur({name : _name, reputation : 1});
    }
    
    function inscriptionEntreprise(string memory _name) public {
        entreprises.push(Entreprise({adresse : msg.sender, nomEntreprise : _name}));
    }
    
    function bannir(address adresse) public {
        require(estAdmin());
        illustrateurs[adresse].reputation = 0;
        bannis.push(adresse);
    }
    
    function banni() private view returns(bool){
        uint nbrBanni = bannis.length;
        for (uint i = 0; i < nbrBanni ; i++) if (msg.sender == bannis[i]) return true;
    }
    
    function estAdmin() private view returns(bool){
        uint nbrAdmin = administrateurs.length;
        for (uint i = 0; i < nbrAdmin; i++) if(msg.sender == administrateurs[i]) return true;
    }
    
    function estEntreprise() internal view returns(bool){
        uint nbrEntreprise = entreprises.length;
        for ( uint i = 0; i < nbrEntreprise; i++) if(msg.sender == entreprises[i].adresse) return true;
    }
    
    
}

contract OffresDeServices is RessourcesHumaines{
    
    enum Statut { Ouverte, Encours, Fermee }
    
    struct Demande {
        address adresseDemandeur;
        Statut statut;
        uint delai;
        string projet;
        uint reputation;
        uint remuneration;
        bytes32 urlDepot;
        address payable candidatRetenu;
        address[] adresseCandidats;
        mapping(address => bool) candidats;
    }
    
    Demande[] public demandes;
    
    constructor(address payable _tresorier) public {
        administrateurs.push(msg.sender);
        tresorier = _tresorier;
    }
    
    function ajouterDemande(string memory _projet, uint _delai, uint _reputation) public payable returns (uint) {
        require(estEntreprise());
        require(msg.value >= 1 finney);
        uint frais = msg.value * 2 / 100;
        tresorier.transfer(frais);
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
    
    function accepterUnCandidat(uint demande, uint candidat) public {
        require(demandes[demande].adresseDemandeur == msg.sender);
        demandes[demande].candidats[demandes[demande].adresseCandidats[candidat]] = true;
    }
    
    function cloturerDemande(uint demande) public {
        require(demandes[demande].statut == Statut.Encours, "Le statut de la demande n'est plus en cours");
        require(demandes[demande].adresseDemandeur == msg.sender, "Cette demande ne vous appartien pas");
        demandes[demande].statut = Statut.Fermee;
        demandes[demande].candidatRetenu.transfer(demandes[demande].remuneration);
        
    }
    
    function postulerPourDemande(uint demande) public {
        require(demandes[demande].statut == Statut.Ouverte);
        require(illustrateurs[msg.sender].reputation > 0);
        require(illustrateurs[msg.sender].reputation >= demandes[demande].reputation);
        demandes[demande].adresseCandidats.push(msg.sender);
    }

    
    function comfirmationDuCandidat(uint demande) public {
        require(demandes[demande].statut == Statut.Ouverte);
        require(demandes[demande].candidats[msg.sender] == true);
        demandes[demande].statut = Statut.Encours;
        demandes[demande].candidatRetenu = msg.sender;
    }
    
    function livraisonURL(uint demande, string memory url) public {
        require(demandes[demande].candidatRetenu == msg.sender);
        require(demandes[demande].statut == Statut.Encours);
        demandes[demande].urlDepot = keccak256(abi.encodePacked(url));
    }
}

























