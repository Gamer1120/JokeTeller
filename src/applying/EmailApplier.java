package applying;

import classifier.Classifier;
import classifier.Document;
import fileparser.FileUtils;

import java.util.List;

public class EmailApplier {
    // The percentage of documents to be used for training
    private static final double TRAINING_PERCENTAGE = 0.10d;

    public static Applier apply(Classifier classifier) {
        Applier applier = new Applier(classifier);
        add(applier, FileUtils.readDocuments("db/emails/ham", "ham"));
        add(applier, FileUtils.readDocuments("db/emails/spam", "spam"));
        return applier;
    }

    private static void add(Applier applier, List<Document> documents) {
        // Calculate the amount of documents for training using the training percentage
        int trainingSetSize = (int) (TRAINING_PERCENTAGE * documents.size());
        documents.stream()
                .limit(trainingSetSize)
                .forEach(applier::train);
        documents.stream()
                .skip(trainingSetSize)
                .map(Document::getText)
                .forEach(applier::add);
    }
}
