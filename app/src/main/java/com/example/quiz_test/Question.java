package com.example.quiz_test;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String question;
    private String[] answers;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correctAnswer;

    public Question(String question, String[] answers, int correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;

    }

    public Question(String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }


    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getAnswer1() {return answer1;}

    public String getAnswer2() {return answer2;}

    public String getAnswer3() {return answer3;}

    public String getAnswer4() {return answer4;}

    public static List<Question> getEasyQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Care este capitala Franței?", new String[]{"Paris", "Londra", "Berlin", "Madrid"}, 0));
        questions.add(new Question("Care este primul oras iluminat stradal", new String[]{"Amsterdam", "Timisoara", "Berlin", "Cluj-Napoca"}, 1));
        questions.add(new Question("Cine a scris 'Romeo și Julieta'?", new String[]{"Charles Dickens", "Jane Austen", "William Shakespeare", "Mark Twain"}, 2));
        questions.add(new Question("Care este cea mai mare planetă din sistemul solar?", new String[]{"Marte", "Pământ", "Jupiter", "Saturn"}, 2));
        questions.add(new Question("Câte continente sunt pe Pământ?", new String[]{"5", "6", "7", "8"}, 2));
        questions.add(new Question("Care este cel mai mare ocean de pe Pământ?", new String[]{"Oceanul Atlantic", "Oceanul Indian", "Oceanul Arctic", "Oceanul Pacific"}, 3));
        questions.add(new Question("Cine a pictat Mona Lisa?", new String[]{"Vincent van Gogh", "Leonardo da Vinci", "Pablo Picasso", "Claude Monet"}, 1));
        questions.add(new Question("Ce țară este cunoscută pentru Turnul Eiffel?", new String[]{"Spania", "Italia", "Franța", "Germania"}, 2));
        questions.add(new Question("Cine este autorul seriei de cărți Harry Potter?", new String[]{"J.K. Rowling", "J.R.R. Tolkien", "George R.R. Martin", "Stephen King"}, 0));
        questions.add(new Question("Care este cea mai mică țară din lume?", new String[]{"Monaco", "Nauru", "Vatican", "San Marino"}, 2));
        return questions;
    }

    public static List<Question> getMediumQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Cine a descoperit America?", new String[]{"Christopher Columbus", "Marco Polo", "Ferdinand Magellan", "James Cook"}, 0));
        questions.add(new Question("Care este cel mai mare mamifer terestru?", new String[]{"Elefantul african", "Rinocerul alb", "Hipopotamul", "Girafa"}, 0));
        questions.add(new Question("Cine a inventat telefonul?", new String[]{"Nikola Tesla", "Thomas Edison", "Alexander Graham Bell", "Guglielmo Marconi"}, 2));
        questions.add(new Question("Câte state are SUA?", new String[]{"50", "48", "52", "51"}, 0));
        questions.add(new Question("Care este formula chimică a apei?", new String[]{"H2O", "CO2", "O2", "N2"}, 0));
        questions.add(new Question("Cine a scris 'Odiseea'?", new String[]{"Vergiliu", "Homer", "Ovidiu", "Sofocle"}, 1));
        questions.add(new Question("Ce este botulismul?", new String[]{"O boală infecțioasă", "Un tip de insectă", "O specie de pește", "O substanță chimică"}, 0));
        questions.add(new Question("Care este cea mai mare insulă din lume?", new String[]{"Islanda", "Madagascar", "Australia", "Groenlanda"}, 3));
        questions.add(new Question("Cine a inventat becul electric?", new String[]{"Thomas Edison", "Alexander Graham Bell", "Nikola Tesla", "Benjamin Franklin"}, 0));
        questions.add(new Question("Ce gaz este esențial pentru respirație?", new String[]{"Azot", "Oxigen", "Dioxid de carbon", "Hidrogen"}, 1));
        return questions;
    }

    public static List<Question> getHardQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Cine a compus Simfonia a 9-a?", new String[]{"Ludwig van Beethoven", "Wolfgang Amadeus Mozart", "Johann Sebastian Bach", "Franz Schubert"}, 0));
        questions.add(new Question("Ce este de Broglie?", new String[]{"O specie de fluture", "Un fizician francez", "Un tip de mineral", "O constelație"}, 1));
        questions.add(new Question("Care este capitala Mongoliei?", new String[]{"Tbilisi", "Astana", "Ulaanbaatar", "Baku"}, 2));
        questions.add(new Question("Cine a scris 'Divina Comedie'?", new String[]{"Homer", "Geoffrey Chaucer", "John Milton", "HomerDante Alighieri"}, 3));
        questions.add(new Question("Cine a descoperit penicilina?", new String[]{"Alexander Fleming", "Louis Pasteur", "Robert Koch", "Edward Jenner"}, 0));
        questions.add(new Question("Câti cromozomi are un om?", new String[]{"45", "46", "44", "48"}, 1));
        questions.add(new Question("Ce este Antares?", new String[]{"O planetă", "O galaxie", "O stea", "Un asteroid"}, 2));
        questions.add(new Question("Cine a pictat 'Noaptea înstelată'?", new String[]{"Salvador Dalí", "Pablo Picasso", "Claude Monet", "Vincent van Gogh"}, 3));
        questions.add(new Question("Ce element chimic are simbolul 'Fe'?", new String[]{"Fier", "Fluor", "Franciu", "Fermiu"}, 0));
        questions.add(new Question("Care este cel mai mic os din corpul uman?", new String[]{"Ciocanul", "Scărița", "Timpanul", "Rotula"}, 1));
        return questions;
    }
}

