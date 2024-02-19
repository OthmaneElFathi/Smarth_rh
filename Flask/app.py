from click import File
from flask import Flask, request, jsonify
from pdfminer.high_level import extract_text
import spacy
import tempfile


app = Flask(__name__)

nlp = spacy.load('en_core_web_sm')

ruler = nlp.add_pipe("entity_ruler", before="ner")
skill_pattern_path = 'jz_skill_patterns.jsonl'
ruler.from_disk(skill_pattern_path)

def extract_skills_from_resume(text):
    skills = set()
    doc = nlp(text)
    for ent in doc.ents:
        if ent.label_ == "SKILL":
            if ent.text.lower() not in map(str.lower, skills):
                skills.add(ent.text)
    return skills

@app.route('/flask/extract_skills', methods=['POST'])
def extract_skills():
    try:
        file_data = request.get_data()
        temp_fd, temp_path = tempfile.mkstemp(suffix=".pdf")
        with open(temp_path, 'wb') as temp_file:
            temp_file.write(file_data)

        text = extract_text(temp_path)
        
        extracted_skills = extract_skills_from_resume(text)

        return jsonify({"extracted_skills": extracted_skills})

    except Exception as e:
        print(str(e))
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
