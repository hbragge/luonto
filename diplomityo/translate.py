import os
from google import genai

def read_paragraphs(filename):
    with open(filename, 'r', encoding='utf-8') as f:
        content = f.read()

    # Split on two or more newlines (paragraphs)
    paragraphs = [p.strip() for p in content.split('\n\n') if p.strip()]
    return paragraphs

def main():
    filename = 'input.txt'
    paragraphs = read_paragraphs(filename)
    client = genai.Client(api_key=os.environ["GEMINI_API_KEY"])

    for i, text in enumerate(paragraphs, 1):
        response = client.models.generate_content(
            model="gemini-2.0-flash", contents=f"Please translate to English, output result only without additional comments: {text}"
        )
        print(response.text)


if __name__ == '__main__':
    main()
