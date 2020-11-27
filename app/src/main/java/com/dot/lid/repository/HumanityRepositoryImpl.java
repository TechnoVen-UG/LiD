package com.dot.lid.repository;

import androidx.annotation.NonNull;

import com.dot.lid.data.CallbackListener;
import com.dot.lid.model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.dot.lid.utils.DatabaseToken.COLL_BILDUNG;
import static com.dot.lid.utils.DatabaseToken.COLL_INTERKULTURELLES_ZUSAMMENLEBEN;
import static com.dot.lid.utils.DatabaseToken.COLL_MIGRATIONSGESCHICHTE;
import static com.dot.lid.utils.DatabaseToken.COLL_RELIGIOSE_VIELFALT;
import static com.dot.lid.utils.DatabaseToken.DOC_MENSCH_UND_GESELLSCHAFT;

public class HumanityRepositoryImpl implements HumanityRepository {

    private static final String TAG = "sayed";

    @Override
    public void retrieve(String languageName, String collectionName, final CallbackListener listener) {
        final List<Question> questionList = new ArrayList<>();
        CollectionReference collection = FirebaseFirestore.getInstance()
                .collection(languageName)
                .document(DOC_MENSCH_UND_GESELLSCHAFT)
                .collection(collectionName);
        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    listener.onSuccess(questionList);
                } else {
                    listener.onFailure();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure();
                    }
                });
    }

    @Override
    public void retrieve(String languageName, CallbackListener listener) {
        final List<Question> questionList = new ArrayList<>();
        DocumentReference documentReference = FirebaseFirestore.getInstance()
                .collection(languageName)
                .document(DOC_MENSCH_UND_GESELLSCHAFT);
        retrieveCOLL_RELIGIOSE_VIELFALT(documentReference, listener, questionList);
    }

    private void retrieveCOLL_RELIGIOSE_VIELFALT(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_RELIGIOSE_VIELFALT).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    retrieveCOLL_BILDUNG(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void retrieveCOLL_BILDUNG(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_BILDUNG).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    retrieveCOLL_MIGRATIONSGESCHICHTE(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void retrieveCOLL_MIGRATIONSGESCHICHTE(final DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_MIGRATIONSGESCHICHTE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    retrieveCOLL_INTERKULTURELLES_ZUSAMMENLEBEN(documentReference, listener, questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

    private void retrieveCOLL_INTERKULTURELLES_ZUSAMMENLEBEN(DocumentReference documentReference, final CallbackListener listener, final List<Question> questionList) {
        documentReference.collection(COLL_INTERKULTURELLES_ZUSAMMENLEBEN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questionList.add(question);
                    }
                    listener.onSuccess(questionList);
                } else {
                    listener.onFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure();
            }
        });
    }

}
